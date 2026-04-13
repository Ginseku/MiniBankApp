package org.bankApp.exception;

public class InsufficientFundsException extends RuntimeException{
    public InsufficientFundsException(){
        super("You have not enough money for this transaction");
    }
}
