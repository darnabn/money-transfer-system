package com.darynaben.moneytransfersystem.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateAccountRequest {

    @NotBlank
    private String contractNumber;

    @NotBlank
    private String iban;

    @NotBlank
    private String currency;

    @NotBlank
    private String holderName;

    @DecimalMin(value = "0.0")
    private BigDecimal initialAmount;
}
