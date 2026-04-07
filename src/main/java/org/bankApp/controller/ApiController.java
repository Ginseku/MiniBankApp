package org.bankApp.controller;

import org.bankApp.entity.Users;
import org.bankApp.service.ApiService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
