package com.darynaben.moneytransfersystem.controller;

import com.darynaben.moneytransfersystem.dto.CreateAccountRequest;
import com.darynaben.moneytransfersystem.dto.CreateAccountResponse;
import com.darynaben.moneytransfersystem.entity.Account;
import com.darynaben.moneytransfersystem.mapper.AccountMapper;
import com.darynaben.moneytransfersystem.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @PostMapping
    public ResponseEntity<CreateAccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest request){
        Account account = accountService.createAccount(accountMapper.toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(accountMapper.toResponse(account));
    }

    @GetMapping("/{iban}")
    public ResponseEntity<CreateAccountResponse> getAccount(@PathVariable String iban) {
        Account account = accountService.getByIban(iban);
        return ResponseEntity.ok(accountMapper.toResponse(account));
    }
}
