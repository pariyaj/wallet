package com.pariyajafari.example.wallet.Model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Valid
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotNull
    @Pattern(regexp = "^09\\d{9}$" , message = "Invalid mobile number format.")
    private String mobileNumber;

    @Column(nullable = false, unique = true)
    @Pattern(regexp = "\\d{10}" , message = "National Id must be 10 digits.")
    @NotNull
    private String nationalId;

    @Column(nullable = false)
    @NotNull
    private String firstName;

    @Column(nullable = false)
    @NotNull
    private String lastName;
    @NotNull
    private LocalDate dateOfBirth;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String militaryStatus;

    @Column(nullable = false, unique = true)
    @Email(message = "Invalid email format.")
    @NotNull
    private String email;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Account> accounts;

}
