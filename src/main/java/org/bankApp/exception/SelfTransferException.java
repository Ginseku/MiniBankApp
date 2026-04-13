package org.bankApp.exception;

public class SelfTransferException extends RuntimeException{
    public SelfTransferException(){
        super("Self-transfer not allowed");
    }
}
