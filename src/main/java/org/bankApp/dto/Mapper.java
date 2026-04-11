package org.bankApp.dto;

import org.bankApp.dto.request.CreateAccountCurrencyRequest;
import org.bankApp.dto.request.RegisterRequest;
import org.bankApp.entity.Account;
import org.bankApp.entity.Users;
import org.bankApp.enums.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    PasswordEncoder passwordEncoder;

    public Mapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Users toEntity(RegisterRequest request){
        Users users = new Users();
        users.setEmail(request.email());
        users.setFullName(request.fullName());
        users.setPassword(passwordEncoder.encode(request.password()));
        users.setRole(Role.USER);
        return users;
    }

    public Account toAccount(CreateAccountCurrencyRequest request, Users users){
        Account account = new Account();
        account.setUser(users);
        account.setCurrency(request.currency());
        return account;
    }

}
