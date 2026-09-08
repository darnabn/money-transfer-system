package com.darynaben.moneytransfersystem.mapper;

import com.darynaben.moneytransfersystem.dto.TransferResponse;
import com.darynaben.moneytransfersystem.entity.Transfer;
import org.springframework.stereotype.Component;

@Component
public class TransferMapper {

    public TransferResponse toResponse(Transfer t) {
        return new TransferResponse(
                t.getId(), t.getSourceIban(), t.getTargetIban(),
                t.getAmount(), t.getCurrency(), t.getStatus(), t.getCreatedAt()
        );
    }
}
