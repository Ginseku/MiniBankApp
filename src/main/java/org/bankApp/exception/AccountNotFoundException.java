package org.bankApp.exception;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(Long id){
        super("Account with " + id + " not found");
    }
}
