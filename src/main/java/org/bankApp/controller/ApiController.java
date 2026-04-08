package org.bankApp.controller;

import org.bankApp.dto.request.CreateAccountCurrencyRequest;
import org.bankApp.entity.Users;
import org.bankApp.service.ApiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    ApiService apiService;

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/getAllUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Users> getAllUsers(){
        return apiService.getAllUsersByAdmin();
    }

    @PostMapping("/createAccount")
    public ResponseEntity<String> createAccount(@RequestBody CreateAccountCurrencyRequest request){
        apiService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Account created");
    }

}
