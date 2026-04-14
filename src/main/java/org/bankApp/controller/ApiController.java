package org.bankApp.controller;

import jakarta.validation.Valid;
import org.bankApp.dto.request.CreateAccountCurrencyRequest;
import org.bankApp.dto.request.TransferRequest;
import org.bankApp.dto.response.CreateAccountResponse;
import org.bankApp.dto.response.TransactionResponse;
import org.bankApp.entity.ApiLog;
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

    @GetMapping("/getLogs")
    @PreAuthorize("hasRole('ADMIN')")
    public List<ApiLog> getLogsByAdmin(){
        return apiService.getLogsByAdmin();
    }

    @DeleteMapping("/deleteUser/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void getLogsByAdmin(@PathVariable Long id){
        apiService.deleteUserByAdmin(id);
    }

    @PostMapping("/createAdmin/{id}")
    public ResponseEntity<String> createAdminById(@PathVariable Long id){
        apiService.createAdminById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body("Admin created");

    }

    @PostMapping("/createAccount")
    public ResponseEntity<CreateAccountResponse> createAccount(@RequestBody CreateAccountCurrencyRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(apiService.createAccount(request));
    }

    @PostMapping("/createTransaction")
    public ResponseEntity<TransactionResponse> creteTransaction(@Valid @RequestBody TransferRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(apiService.createTransaction(request));
    }


}
