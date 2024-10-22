package com.pariyajafari.example.wallet.Test;

import static javax.management.Query.times;
import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
import static jdk.jfr.internal.jfc.model.Constraint.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.pariyajafari.example.wallet.Model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountService accountService;

    private Account account;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        account = new Account();
        account.setId(1L);
        account.setBalance(1000000.0);
    }

    @Test
    public void testDeposit_Success() {
        when(accountService.getAccountById(1L)).thenReturn(account);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());

        Transaction result = transactionService.deposit(1L, 50000.0);

        assertEquals(1050000.0, account.getBalance());
        verify(accountService, times(1)).updateAccount(1L, account);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    public void testWithdraw_Success() {
        when(accountService.getAccountById(1L)).thenReturn(account);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());

        Transaction result = transactionService.withdraw(1L, 50000.0);

        assertEquals(950000.0, account.getBalance());
        verify(accountService, times(1)).updateAccount(1L, account);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    public void testWithdraw_InsufficientBalance() {
        when(accountService.getAccountById(1L)).thenReturn(account);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.withdraw(1L, 1500000.0);
        });

        assertEquals("Insufficient balance for withdrawal.", exception.getMessage());
        verify(accountService, times(0)).updateAccount(anyLong(), any(Account.class));
        verify(transactionRepository, times(0)).save(any(Transaction.class));
    }

    @Test
    public void testWithdraw_DailyLimitExceeded() {
        when(accountService.getAccountById(1L)).thenReturn(account);

        when(transactionRepository.findDailyWithdrawals(1L, LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT), LocalDateTime.now()))
                .thenReturn(950000.0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.withdraw(1L, 100000.0);
        });

        assertEquals("Daily withdrawal limit exceeded.", exception.getMessage());
        verify(accountService, times(0)).updateAccount(anyLong(), any(Account.class));
        verify(transactionRepository, times(0)).save(any(Transaction.class));
    }
}

