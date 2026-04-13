package org.bankApp.exception;

import jakarta.persistence.OptimisticLockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handlerGeneral(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Unexpected error!");
    }
    @ExceptionHandler(OptimisticLockException.class)
    public ResponseEntity<String> handlerOptimisticLock(OptimisticLockException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Transaction conflict. Please try again");
    }
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> handlerAccountNotFound(AccountNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handlerUserNotFound(UserNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<String> InsufficientFundsException(InsufficientFundsException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
    @ExceptionHandler(SelfTransferException.class)
    public ResponseEntity<String> handlerSelfTransferException(SelfTransferException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
    @ExceptionHandler(WrongAmountException.class)
    public ResponseEntity<String> handlerWrongAmountException(WrongAmountException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
    @ExceptionHandler(LockedException.class)
    public ResponseEntity<String> handlerLockedAccount(LockedException e) {
        return ResponseEntity
                .status(HttpStatus.LOCKED)
                .body(e.getMessage());
    }
}
