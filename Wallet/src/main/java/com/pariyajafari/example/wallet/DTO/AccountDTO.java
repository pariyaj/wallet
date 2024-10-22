package com.pariyajafari.example.wallet.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class AccountDTO {

    @Pattern(regexp = "\\d{10,}" , message = "Account number must be at least 10 digits.")
    @NotNull
    private String accountNumber;
    @NotNull
    private Double balance;
    @NotNull
    @Pattern(regexp = "^IR\\d{24}$" , message = "Invalid IBAN format. Must start with 'IR' and be 24 digits long.")
    private String iban;

}
