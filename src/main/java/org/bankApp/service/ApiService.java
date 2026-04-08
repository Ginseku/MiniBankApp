package org.bankApp.service;

import org.bankApp.dto.request.CreateAccountCurrencyRequest;
import org.bankApp.entity.Account;
import org.bankApp.entity.Users;
import org.bankApp.repository.AccountRepository;
import org.bankApp.repository.UsersRepository;
import org.bankApp.security.JwtService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiService {

    UsersRepository usersRepository;
    JwtService jwtService;
    AccountRepository accountRepository;

    public ApiService(UsersRepository usersRepository, JwtService jwtService, AccountRepository accountRepository) {
        this.usersRepository = usersRepository;
        this.jwtService = jwtService;
        this.accountRepository = accountRepository;
    }

    public List<Users> getAllUsersByAdmin() {
        return usersRepository.findAll();
    }

    public void createAccount(CreateAccountCurrencyRequest request){
        Users users = usersRepository.findById(request.userId())
                .orElseThrow( () -> new UsernameNotFoundException("User with this id not fount " + request.userId()));

        Account account = new Account();
        account.setUser(users);
        account.setCurrency(request.currency());

        accountRepository.save(account);

    }

}
