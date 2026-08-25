package com.darynaben.moneytransfersystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class CreateAccountResponse {
    private Long id;
    private String contractNumber;
    private String iban;
    private BigDecimal amount;
    private String currency;
    private String holderName;
}
