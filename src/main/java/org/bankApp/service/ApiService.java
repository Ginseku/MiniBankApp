package org.bankApp.service;

import org.bankApp.dto.Mapper;
import org.bankApp.dto.request.CreateAccountCurrencyRequest;
import org.bankApp.dto.response.CreateAccountResponse;
import org.bankApp.entity.Account;
import org.bankApp.entity.Users;
import org.bankApp.exception.UserNotFoundException;
import org.bankApp.repository.AccountRepository;
import org.bankApp.repository.UsersRepository;
import org.bankApp.security.JwtService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiService {

    private final UsersRepository usersRepository;
    private final JwtService jwtService;
    private final AccountRepository accountRepository;
    private final Mapper mapper;

    public ApiService(UsersRepository usersRepository, JwtService jwtService, AccountRepository accountRepository, Mapper mapper) {
        this.usersRepository = usersRepository;
        this.jwtService = jwtService;
        this.accountRepository = accountRepository;
        this.mapper = mapper;
    }

    public List<Users> getAllUsersByAdmin() {
        return usersRepository.findAll();
    }

    public CreateAccountResponse createAccount(CreateAccountCurrencyRequest request){
        Users users = usersRepository.findById(request.userId())
                .orElseThrow( () -> new UserNotFoundException(request.userId()));

        Account saved = accountRepository.save(mapper.toAccount(request,users));
       return new CreateAccountResponse(saved.getCurrency());

    }

}
