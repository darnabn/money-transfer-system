package com.darynaben.moneytransfersystem.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransferRequest {

    @NotBlank
    private String sourceIban;

    @NotBlank
    private String targetIban;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amount;
}
