package org.bankApp.service;

import org.bankApp.dto.Mapper;
import org.bankApp.dto.request.RegisterRequest;
import org.bankApp.entity.UserPrinciples;
import org.bankApp.entity.Users;
import org.bankApp.repository.UsersRepository;
import org.bankApp.security.JwtService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    UsersRepository usersRepository;
    Mapper mapper;
    JwtService jwtService;

    public AuthService(UsersRepository usersRepository, Mapper mapper, JwtService jwtService) {
        this.usersRepository = usersRepository;
        this.mapper = mapper;
        this.jwtService = jwtService;
    }

    public String registerUser(RegisterRequest request){
        UserDetails users = new UserPrinciples(usersRepository.save(mapper.toEntity(request)));
        return jwtService.createToken(users);
    }

}
