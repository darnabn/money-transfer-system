package com.darynaben.moneytransfersystem.controller;

import com.darynaben.moneytransfersystem.dto.TransferRequest;
import com.darynaben.moneytransfersystem.dto.TransferResponse;
import com.darynaben.moneytransfersystem.entity.Transfer;
import com.darynaben.moneytransfersystem.mapper.TransferMapper;
import com.darynaben.moneytransfersystem.service.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/api/transfers")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;
    private final TransferMapper transferMapper;

    @PostMapping
    public ResponseEntity<TransferResponse> transfer(@Valid @RequestBody TransferRequest request){
        Transfer transfer = transferService.transfer(request.getSourceIban(), request.getTargetIban(), request.getAmount());
        return ResponseEntity.status(HttpStatus.CREATED).body(transferMapper.toResponse(transfer));
    }
}
