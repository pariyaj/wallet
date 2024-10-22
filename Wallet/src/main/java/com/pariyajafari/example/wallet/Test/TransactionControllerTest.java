package com.pariyajafari.example.wallet.Test;

import com.pariyajafari.example.wallet.Controller.TransactionController;
import com.pariyajafari.example.wallet.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;

import static javax.management.Query.times;
import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDeposit_Success() throws Exception {
        mockMvc.perform(post("/api/transactions/deposit")
                        .contentType("application/json")
                        .content("{\"accountId\":1,\"amount\":50000.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(50000.0));

        verify(transactionService, times(1)).deposit(1L, 50000.0);
    }

    @Test
    public void testWithdraw_Success() throws Exception {
        mockMvc.perform(post("/api/transactions/withdraw")
                        .contentType("application/json")
                        .content("{\"accountId\":1,\"amount\":50000.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(50000.0));

        verify(transactionService, times(1)).withdraw(1L, 50000.0);
    }

    @Test
    public void testWithdraw_InsufficientBalance() throws Exception {
        doThrow(new IllegalArgumentException("Insufficient balance for withdrawal."))
                .when(transactionService).withdraw(1L, 150000.0);

        mockMvc.perform(post("/api/transactions/withdraw")
                        .contentType("application/json")
                        .content("{\"accountId\":1,\"amount\":150000.0}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Insufficient balance for withdrawal."));

        verify(transactionService, times(1)).withdraw(1L, 150000.0);
    }
}
