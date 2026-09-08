package com.darynaben.moneytransfersystem.exception;

import java.math.BigDecimal;

public class InsufficientFundsException extends RuntimeException{
    public InsufficientFundsException(String iban, BigDecimal available, BigDecimal requested){
        super("Insufficient funds on account %s: available %s, requested %s"
                .formatted(iban, available, requested));
    }
}
