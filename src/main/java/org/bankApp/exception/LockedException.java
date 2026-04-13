package org.bankApp.exception;

public class LockedException extends RuntimeException{
    public LockedException(){
        super("Account locked");
    }
}
