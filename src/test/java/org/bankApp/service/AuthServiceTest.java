package org.bankApp.service;

import org.bankApp.dto.Mapper;
import org.bankApp.dto.request.LoginRequest;
import org.bankApp.dto.request.RegisterRequest;
import org.bankApp.entity.Users;
import org.bankApp.repository.UsersRepository;
import org.bankApp.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UsersRepository usersRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtService jwtService;
    @Mock
    private Mapper mapper;

    @InjectMocks
    private AuthService authService;

    // ===================== REGISTER =====================
    @Test
    void registerUser_shouldReturnToken_whenEmailIsFree() {
        RegisterRequest request = new RegisterRequest("nikk@gmail.com", "123456789", "Nikkki");

        Users user = new Users();

        when(usersRepository.findByEmail(request.email()))
                .thenReturn(Optional.empty());
        when(mapper.toEntity(request)).thenReturn(user);
        when(usersRepository.save(user)).thenReturn(user);
        when(jwtService.createToken(any())).thenReturn("token");

        String result = authService.registerUser(request);

        assertEquals("token", result);


        verify(usersRepository).findByEmail(request.email());
        verify(mapper).toEntity(request);
        verify(usersRepository).save(user);
        verify(jwtService).createToken(any());
    }

    @Test
    void register_emailAlreadyExists_throwsException() {
        RegisterRequest request = new RegisterRequest("nikk@gmail.com", "123456789", "Nikkki");


        when(usersRepository.findByEmail(request.email())).thenReturn(Optional.of(new Users()));

        assertThrows(RuntimeException.class, () -> authService.registerUser(request), "Should throw when email already exists");

        verify(usersRepository, never()).save(any());
        verify(mapper, never()).toEntity(any());
        verify(jwtService, never()).createToken(any());
    }

    // ===================== LOGIN =====================

    @Test
    void loginUser_shouldReturnToken_whenCredentialsAreValid() {
        LoginRequest request = new LoginRequest("nikk@gmail.com", "123456789");

        Users users = new Users();

        when(usersRepository.findByEmail(request.email())).thenReturn(Optional.of(users));
        when(jwtService.createToken(any(UserDetails.class))).thenReturn("Token");

        String result = authService.loginUser(request);

        assertEquals("Token", result);

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(usersRepository).findByEmail(request.email());
        verify(jwtService).createToken(any(UserDetails.class));// в целом можно смотреть на условие when и брать от туда этот кусок jwtService.createToken(any(UserDetails.class)))
    }

    @Test
    void loginUser_shouldThrowException_WhenUserWrongPassword() {
        LoginRequest request = new LoginRequest("nikk@gmail.com", "123456789");

        doThrow(new BadCredentialsException("Bad credential"))
                .when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        assertThrows(
                BadCredentialsException.class,
                () -> authService.loginUser(request)
        );

        verify(usersRepository, never()).findByEmail(any());
        verify(jwtService, never()).createToken(any());

    }

    @Test
    void loginUser_shouldThrowUsernameNotFoundException_whenUserNotFound() {
        LoginRequest request = new LoginRequest("nikk@gmail.com", "123456789");

        when(usersRepository.findByEmail(request.email())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> authService.loginUser(request));

        verify(jwtService, never()).createToken(any());
    }

}