package org.bankApp.exception;

public class WrongAmountException extends RuntimeException{
    public WrongAmountException(){
        super("Amount can not be 0 or negative");
    }
}
