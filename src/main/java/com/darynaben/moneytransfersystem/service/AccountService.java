package com.darynaben.moneytransfersystem.service;

import com.darynaben.moneytransfersystem.entity.Account;
import com.darynaben.moneytransfersystem.repository.AccountRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public Account createAccount(Account account){
        accountRepository.findByIban(account.getIban())
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Account with IBAN " + account.getIban() + " already exists");
                });
        if(account.getAmount() == null){
            account.setAmount(BigDecimal.ZERO);
        }

        return accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    public Account getByIban(String iban){
        return accountRepository.findByIban(iban)
                .orElseThrow(() -> new IllegalArgumentException("Account not found " + iban));
    }
}
