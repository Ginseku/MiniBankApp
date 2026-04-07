package org.bankApp.service;

import org.bankApp.dto.Mapper;
import org.bankApp.dto.request.LoginRequest;
import org.bankApp.dto.request.RegisterRequest;
import org.bankApp.entity.UserPrinciples;
import org.bankApp.entity.Users;
import org.bankApp.repository.UsersRepository;
import org.bankApp.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    UsersRepository usersRepository;
    Mapper mapper;
    JwtService jwtService;
    AuthenticationManager authenticationManager;

    public AuthService(UsersRepository usersRepository, Mapper mapper, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.usersRepository = usersRepository;
        this.mapper = mapper;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public String registerUser(RegisterRequest request){
        UserDetails users = new UserPrinciples(usersRepository.save(mapper.toEntity(request)));
        return jwtService.createToken(users);
    }

    public String loginUser(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(),request.password())
        );

        UserDetails users = new UserPrinciples(usersRepository.findByEmail(request.email())
                        .orElseThrow( () -> new UsernameNotFoundException("Can't find user with this email")));

        return jwtService.createToken(users);
    }

}
