package com.darynaben.moneytransfersystem.mapper;

import com.darynaben.moneytransfersystem.dto.CreateAccountRequest;
import com.darynaben.moneytransfersystem.dto.CreateAccountResponse;
import com.darynaben.moneytransfersystem.entity.Account;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountMapper {

    public Account toEntity(CreateAccountRequest request){
        Account account = new Account();
        account.setContractNumber(request.getContractNumber());
        account.setIban(request.getIban());
        account.setCurrency(request.getCurrency());
        account.setHolderName(request.getHolderName());
        account.setAmount(
                request.getInitialAmount() != null
                        ? request.getInitialAmount()
                        : BigDecimal.ZERO
        );

        return account;
    }

    public CreateAccountResponse toResponse(Account account) {
        return new CreateAccountResponse(
                account.getId(),
                account.getContractNumber(),
                account.getIban(),
                account.getAmount(),
                account.getCurrency(),
                account.getHolderName()
        );
    }
}
