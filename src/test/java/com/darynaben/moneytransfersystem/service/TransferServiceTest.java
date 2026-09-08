package com.darynaben.moneytransfersystem.service;

import com.darynaben.moneytransfersystem.entity.Transfer;
import com.darynaben.moneytransfersystem.exception.InsufficientFundsException;
import com.darynaben.moneytransfersystem.exception.TransferConflictException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.OptimisticLockingFailureException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {

    @Mock
    private TransferProcessor transferProcessor;

    @InjectMocks
    private TransferService transferService;

    private static final String SRC = "KZ001";
    private static final String TGT = "KZ002";
    private static final BigDecimal AMOUNT = new BigDecimal("100.00");

    @Test
    void shouldReturnTransfer_whenProcessorSucceedsFirstTime(){
        Transfer expected = new Transfer();
        when(transferProcessor.process(SRC, TGT, AMOUNT)).thenReturn(expected);

        Transfer result = transferService.transfer(SRC, TGT, AMOUNT);

        assertSame(expected, result);
        verify(transferProcessor, times(1)).process(SRC, TGT, AMOUNT);
    }

    @Test
    void shouldRetryAndSucceed_whenFirstAttemptHitsLockConflict(){
        Transfer expected = new Transfer();
        when(transferProcessor.process(SRC, TGT, AMOUNT))
                .thenThrow(new OptimisticLockingFailureException("conflict"))
                .thenReturn(expected);

        Transfer result = transferService.transfer(SRC, TGT, AMOUNT);

        assertSame(expected, result);
        verify(transferProcessor, times(2)).process(SRC, TGT, AMOUNT);
    }

    @Test
    void shouldThrowConflict_whenAllAttemptsFail() {
        when(transferProcessor.process(SRC, TGT, AMOUNT))
                .thenThrow(new OptimisticLockingFailureException("conflict"));

        assertThrows(TransferConflictException.class,
                () -> transferService.transfer(SRC, TGT, AMOUNT));

        verify(transferProcessor, times(3)).process(SRC, TGT, AMOUNT);
    }

    @Test
    void shouldNotRetry_whenBusinessExceptionThrown() {
        when(transferProcessor.process(SRC, TGT, AMOUNT))
                .thenThrow(new InsufficientFundsException(SRC, BigDecimal.TEN, AMOUNT));

        assertThrows(InsufficientFundsException.class,
                () -> transferService.transfer(SRC, TGT, AMOUNT));

        verify(transferProcessor, times(1)).process(SRC, TGT, AMOUNT);
    }
}
