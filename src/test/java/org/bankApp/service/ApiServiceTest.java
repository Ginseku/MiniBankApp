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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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

}