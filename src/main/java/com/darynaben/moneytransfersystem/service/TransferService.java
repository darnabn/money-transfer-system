package com.darynaben.moneytransfersystem.service;

import com.darynaben.moneytransfersystem.entity.Transfer;
import com.darynaben.moneytransfersystem.exception.TransferConflictException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransferService {

    private static final int MAX_ATTEMPTS = 3;

    private final TransferProcessor processor;

    public Transfer transfer(String sourceIban, String targetIban, BigDecimal amount){
        OptimisticLockingFailureException lastFailure = null;

        for(int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++){
            try{
                return processor.process(sourceIban, targetIban, amount);
            } catch (OptimisticLockingFailureException e){
                lastFailure = e;
                log.warn("Optimistic lock conflict on transfer {} -> {}, attempt {}/{}",
                        sourceIban, targetIban, attempt, MAX_ATTEMPTS);
                if(attempt < MAX_ATTEMPTS){
                    sleepBackoff(attempt);
                }
            }
        }
        throw new TransferConflictException(sourceIban, targetIban, MAX_ATTEMPTS, lastFailure);
    }

    private void sleepBackoff(int attempts){
        try{
            long baseMillis = 20L * attempts;
            long jitter = ThreadLocalRandom.current().nextLong(10);
            Thread.sleep(baseMillis + jitter);
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted during retry backoff", e);
        }
    }
}
