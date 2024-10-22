package com.pariyajafari.example.wallet.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

    @NotNull(message = "Account ID can't be null")
    private Long accountId;
    @Min( value = 1 , message = "Amount can't be zero.")
    private Double amount;
    private LocalDateTime date;
    private String transactionType;  // Deposit or Withdraw

}