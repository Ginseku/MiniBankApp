package org.bankApp.service;

import org.bankApp.enums.Role;
import org.bankApp.exception.*;
import org.bankApp.repository.ApiLogRepository;
import org.bankApp.saver.LogSaver;
import org.bankApp.dto.Mapper;
import org.bankApp.dto.request.CreateAccountCurrencyRequest;
import org.bankApp.dto.request.TransferRequest;
import org.bankApp.dto.response.CreateAccountResponse;
import org.bankApp.dto.response.TransactionResponse;
import org.bankApp.entity.*;
import org.bankApp.enums.TransactionStatus;
import org.bankApp.enums.TransactionType;
import org.bankApp.repository.AccountRepository;
import org.bankApp.repository.UsersRepository;
import org.bankApp.saver.TransactionSaver;
import org.bankApp.security.JwtService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApiService {

    private final UsersRepository usersRepository;
    private final JwtService jwtService;
    private final AccountRepository accountRepository;
    private final Mapper mapper;
    private final LogSaver logSaver;
    private final TransactionSaver transactionSaver;
    private final ApiLogRepository apiLogRepository;

    public ApiService(UsersRepository usersRepository, JwtService jwtService, AccountRepository accountRepository, Mapper mapper, LogSaver logSaver, TransactionSaver transactionSaver, ApiLogRepository apiLogRepository) {
        this.usersRepository = usersRepository;
        this.jwtService = jwtService;
        this.accountRepository = accountRepository;
        this.mapper = mapper;
        this.logSaver = logSaver;
        this.transactionSaver = transactionSaver;
        this.apiLogRepository = apiLogRepository;
    }

    @Transactional
    public TransactionResponse createTransaction(TransferRequest request) {
        ApiLog log = new ApiLog();

        //Проверки которые до бд
        if (request.from_account().equals(request.to_account())) { //Кому перевод, если себе то ошибка
            fail(log, request, new SelfTransferException());
        }
        if (request.amount().compareTo(BigDecimal.ZERO) <= 0) {//валидная ли сумма?
            fail(log, request, new WrongAmountException());
        }

        //Работа с бд
        Users fromUser = usersRepository.findById(request.from_account())
                .orElseThrow(() -> new UserNotFoundException(request.from_account()));
        Users toUser = usersRepository.findById(request.to_account())
                .orElseThrow(() -> new UserNotFoundException(request.to_account()));


        if (fromUser.isLocked() && toUser.isLocked()) {//юзер в блоке?
            fail(log, request, new LockedException());
        }

        Account fromAccount = accountRepository.findByUserIdAndCurrency(fromUser.getId(), request.currency())
                .orElseThrow(() -> new AccountNotFoundException(request.from_account()));
        Account toAccount = accountRepository.findByUserIdAndCurrency(toUser.getId(), request.currency())
                .orElseThrow(() -> new AccountNotFoundException(request.to_account()));


        if (fromAccount.getBalance().compareTo(request.amount()) < 0) {//хватает ли денег
            fail(log, request, new InsufficientFundsException());
        }

        //Бизнес логика
        fromAccount.setBalance(
                fromAccount.getBalance().subtract(request.amount())
        );
        toAccount.setBalance(
                toAccount.getBalance().add(request.amount())
        );
        //accountRepository.save(toAccount); // не нужен потому что есть @Transaction

        transactionSaver.saveTransaction(request, fromAccount, toAccount);
        logSaver.saveLogWhenSuccess(log, request);

        return new TransactionResponse(
                request.from_account(),
                request.to_account(),
                request.amount(),
                TransactionStatus.COMPLETED,
                TransactionType.TRANSFER,
                LocalDateTime.now()
        );

    }

    private void fail(ApiLog log, TransferRequest request, RuntimeException ex) {
        logSaver.saveLogWhenFailed(log, request);
        throw ex;
    }

    public List<Users> getAllUsersByAdmin() {
        Pageable pageable = PageRequest.of(0,5, Sort.by("createdAt").descending());
        return usersRepository.findAll(pageable).getContent();
    }

    public List<ApiLog> getLogsByAdmin(){
        Pageable pageable = PageRequest.of(0,5, Sort.by("createdAt").descending());
        return apiLogRepository.findAll(pageable).getContent();
    }

    public void deleteUserByAdmin(Long id) {
        usersRepository.deleteById(id);
    }

    public CreateAccountResponse createAccount(CreateAccountCurrencyRequest request) {
        Users users = usersRepository.findById(request.userId())
                .orElseThrow(() -> new UserNotFoundException(request.userId()));

        Account saved = accountRepository.save(mapper.toAccount(request, users));
        return new CreateAccountResponse(saved.getCurrency());

    }

    public void createAdminById(Long id){
        Users users = usersRepository.findById(id)
                .orElseThrow( () -> new UserNotFoundException(id));
        users.setRole(Role.ADMIN);
        usersRepository.save(users);
    }

}
