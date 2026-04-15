package org.bankApp.service;

import org.bankApp.dto.Mapper;
import org.bankApp.dto.request.CreateAccountCurrencyRequest;
import org.bankApp.dto.request.TransferRequest;
import org.bankApp.dto.response.CreateAccountResponse;
import org.bankApp.dto.response.TransactionResponse;
import org.bankApp.entity.Account;
import org.bankApp.entity.Users;
import org.bankApp.enums.Role;
import org.bankApp.enums.TransactionStatus;
import org.bankApp.enums.TransactionType;
import org.bankApp.exception.*;
import org.bankApp.repository.AccountRepository;
import org.bankApp.repository.UsersRepository;
import org.bankApp.saver.LogSaver;
import org.bankApp.saver.TransactionSaver;
import org.bankApp.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApiServiceTest {

    @Mock
    private UsersRepository usersRepository;
    @Mock
    private JwtService jwtService;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private Mapper mapper;
    @Mock
    private LogSaver logSaver;
    @Mock
    private TransactionSaver transactionSaver;

    @InjectMocks
    private ApiService apiService;

    @Test
    void createAccountAreSuccess_Return(){
        CreateAccountCurrencyRequest request = new CreateAccountCurrencyRequest(1L,"USD");

        Users users = new Users();
        Account accountBeforeSave = new Account();
        Account accountAfterSave = new Account();
        accountAfterSave.setCurrency("USD");

        when(usersRepository.findById(1L)).thenReturn(Optional.of(users));
        when(mapper.toAccount(request,users)).thenReturn(accountBeforeSave);
        when(accountRepository.save(accountBeforeSave)).thenReturn(accountAfterSave);


        CreateAccountResponse result = apiService.createAccount(request);
        assertEquals("USD", result.currency());
    }

    @Test
    void createAccount_ThrowException_UserNotFound(){
        CreateAccountCurrencyRequest request = new CreateAccountCurrencyRequest(999L,"USD");

        Users users = new Users();

        when(usersRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> apiService.createAccount(request));

    }

    @Test
    void createAdmin_ShouldReturnAdminRole(){
        Long userId = 1L;

        Users users = new Users();

        users.setId(userId);
        users.setRole(Role.USER);

        when(usersRepository.findById(1L)).thenReturn(Optional.of(users));

        apiService.createAdminById(userId);

        assertEquals(Role.ADMIN, users.getRole());
        verify(usersRepository).save(users);
    }

    @Test
    void createAdmin_WithWrongUserId_ShouldReturnException(){
        Long userId = 1L;

        when(usersRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> apiService.createAdminById(userId));
        verify(usersRepository, never()).save(any());
    }

    private Users fromUser;
    private Users toUser;
    private Account fromAccount;
    private Account toAccount;

    @BeforeEach
    void setUp() {
        fromUser = new Users();
        fromUser.setId(1L);
        fromUser.setLocked(false);

        toUser = new Users();
        toUser.setId(2L);
        toUser.setLocked(false);

        fromAccount = new Account();
        fromAccount.setId(1L);
        fromAccount.setBalance(new BigDecimal("1000"));
        fromAccount.setCurrency("USD");

        toAccount = new Account();
        toAccount.setId(2L);
        toAccount.setBalance(new BigDecimal("500"));
        toAccount.setCurrency("USD");
    }

    @Test
    void CreateTransaction_Success(){

        TransferRequest request = new TransferRequest(1L,2L, new BigDecimal("300"), TransactionType.TRANSFER, "USD");

        when(usersRepository.findById(1L)).thenReturn(Optional.of(fromUser));
        when(usersRepository.findById(2L)).thenReturn(Optional.of(toUser));
        when(accountRepository.findByUserIdAndCurrency(1L,"USD")).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findByUserIdAndCurrency(2L,"USD")).thenReturn(Optional.of(toAccount));

        TransactionResponse response = apiService.createTransaction(request);

        assertEquals(new BigDecimal("700"), fromAccount.getBalance());
        assertEquals(new BigDecimal("800"), toAccount.getBalance());

        verify(transactionSaver).saveTransaction(request, fromAccount, toAccount);
        verify(logSaver).saveLogWhenSuccess(any(), eq(request));

        assertEquals(TransactionStatus.COMPLETED, response.status());
    }

    @Test
    void createTransaction_selfTransfer_shouldThrow(){
        TransferRequest request = new TransferRequest(1L,1L, new BigDecimal("300"), TransactionType.TRANSFER, "USD");

        assertThrows(SelfTransferException.class, () -> apiService.createTransaction(request));
        verify(logSaver).saveLogWhenFailed(any(), eq(request));
    }

    @Test
    void createTransaction_InvalidTransactionSumShouldThrow(){
        TransferRequest request = new TransferRequest(1L,2L, new BigDecimal("0"), TransactionType.TRANSFER, "USD");

        assertThrows(WrongAmountException.class,() -> apiService.createTransaction(request));
        verify(logSaver).saveLogWhenFailed(any(), eq(request));
    }

    @Test
    void createTransaction_NotEnoughMoneyForTransactionShouldThrow(){
        fromAccount.setBalance(new BigDecimal("50"));
        TransferRequest request = new TransferRequest(1L,2L, new BigDecimal("200"), TransactionType.TRANSFER, "USD");

        when(usersRepository.findById(1L)).thenReturn(Optional.of(fromUser));
        when(usersRepository.findById(2L)).thenReturn(Optional.of(toUser));
        when(accountRepository.findByUserIdAndCurrency(1L,"USD")).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findByUserIdAndCurrency(2L,"USD")).thenReturn(Optional.of(toAccount));

        assertThrows(InsufficientFundsException.class, () -> apiService.createTransaction(request));
        verify(logSaver).saveLogWhenFailed(any(), eq(request));
    }
    @Test
    void createTransaction_accountNotFound() {
        TransferRequest request = new TransferRequest(1L,2L, new BigDecimal("200"), TransactionType.TRANSFER, "USD");


        when(usersRepository.findById(1L)).thenReturn(Optional.of(fromUser));
        when(usersRepository.findById(2L)).thenReturn(Optional.of(toUser));

        when(accountRepository.findByUserIdAndCurrency(1L,"USD"))
                .thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class,
                () -> apiService.createTransaction(request));
    }

    @Test
    void createTransaction_userNotFound() {
        TransferRequest request = new TransferRequest(1L,2L, new BigDecimal("200"), TransactionType.TRANSFER, "USD");

        when(usersRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> apiService.createTransaction(request));
    }
}