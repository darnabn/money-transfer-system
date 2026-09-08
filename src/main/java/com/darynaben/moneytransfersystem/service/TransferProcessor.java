package com.darynaben.moneytransfersystem.service;

import com.darynaben.moneytransfersystem.entity.Account;
import com.darynaben.moneytransfersystem.entity.Transfer;
import com.darynaben.moneytransfersystem.entity.TransferStatus;
import com.darynaben.moneytransfersystem.exception.InsufficientFundsException;
import com.darynaben.moneytransfersystem.repository.AccountRepository;
import com.darynaben.moneytransfersystem.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class TransferProcessor {

    private final AccountRepository accountRepository;
    private final TransferRepository transferRepository;

    @Transactional
    public Transfer process(String sourceIban, String targetIban, BigDecimal amount){

        if(sourceIban.equals(targetIban)){
            throw new IllegalArgumentException("Source and target accounts must differ");
        }
        if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Amount must be positive");
        }

        Account source = accountRepository.findByIban(sourceIban)
                .orElseThrow(() -> new IllegalArgumentException("Source account not found: " + sourceIban));
        Account target = accountRepository.findByIban(targetIban)
                .orElseThrow(() -> new IllegalArgumentException("Target account not found: " + targetIban));

        if(!source.getCurrency().equals(target.getCurrency())){
            throw new IllegalArgumentException("Currency mismatch");
        }
        if(source.getAmount().compareTo(amount) < 0){
            throw new InsufficientFundsException(sourceIban, source.getAmount(), amount);
        }

        source.setAmount(source.getAmount().subtract(amount));
        target.setAmount(target.getAmount().add(amount));

        accountRepository.save(source);
        accountRepository.save(target);

        Transfer transfer = new Transfer();
        transfer.setSourceIban(sourceIban);
        transfer.setTargetIban(targetIban);
        transfer.setAmount(amount);
        transfer.setCurrency(source.getCurrency());
        transfer.setStatus(TransferStatus.COMPLETED);

        return transferRepository.save(transfer);
    }
}
