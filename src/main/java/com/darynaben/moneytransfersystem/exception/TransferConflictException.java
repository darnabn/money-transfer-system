package com.darynaben.moneytransfersystem.exception;

public class TransferConflictException extends RuntimeException{
    public TransferConflictException(String sourceIban, String targetIban, int attempts, Throwable cause){
        super("Transfer %s -> %s failed after %d attempts due to concurrent modification"
                .formatted(sourceIban, targetIban, attempts), cause);
    }
}
