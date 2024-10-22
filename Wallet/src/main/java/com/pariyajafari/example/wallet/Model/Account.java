package com.pariyajafari.example.wallet.Model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Valid
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Pattern(regexp = "\\d{10,}" , message = "Account number must be at least 10 digits.")
    @NotNull
    private String accountNumber;

    @Column(nullable = false)
    @NotNull
    private Double balance;

    @Column(nullable = false, updatable = false)
    @NotNull
    private LocalDate createdAt;

    @Column(nullable = false, unique = true)
    @NotNull
    @Pattern(regexp = "^IR\\d{24}$" , message = "Invalid IBAN format. Must start with 'IR' and be 24 digits long.")
    private String iban;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

}




