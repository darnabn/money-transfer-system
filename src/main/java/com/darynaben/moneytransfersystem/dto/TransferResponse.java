package com.darynaben.moneytransfersystem.dto;

import com.darynaben.moneytransfersystem.entity.TransferStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@AllArgsConstructor
public class TransferResponse {
    private Long id;
    private String sourceIban;
    private String targetIban;
    private BigDecimal amount;
    private String currency;
    private TransferStatus status;
    private Instant createdAt;
}
