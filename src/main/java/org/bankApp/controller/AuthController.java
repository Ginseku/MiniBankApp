package org.bankApp.controller;

import jakarta.validation.Valid;
import org.bankApp.dto.request.LoginRequest;
import org.bankApp.dto.request.RegisterRequest;
import org.bankApp.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("auth/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest request){
        return new ResponseEntity<>(authService.registerUser(request), HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody LoginRequest request){
        return new ResponseEntity<>(authService.loginUser(request), HttpStatus.ACCEPTED);

    }

}
