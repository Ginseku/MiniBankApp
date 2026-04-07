package org.bankApp.service;

import org.bankApp.entity.Users;
import org.bankApp.repository.UsersRepository;
import org.bankApp.security.JwtService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiService {

    UsersRepository usersRepository;
    JwtService jwtService;

    public ApiService(UsersRepository usersRepository, JwtService jwtService) {
        this.usersRepository = usersRepository;
        this.jwtService = jwtService;
    }

    public List<Users> getAllUsersByAdmin() {
        return usersRepository.findAll();
    }

}
