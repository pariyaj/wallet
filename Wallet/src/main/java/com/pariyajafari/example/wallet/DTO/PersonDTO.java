package com.pariyajafari.example.wallet.DTO;

import com.pariyajafari.example.wallet.Model.Gender;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class PersonDTO {

    @NotNull(message = "First name can't be null.")
    @Size(min = 3 , max = 50, message = "First name must be between 3 and 50 characters.")
    private String firstName;

    @NotNull(message = "Last name can't be null.")
    @Size(min = 3 , max = 50, message = "Last name must be between 3 and 50 characters.")
    private String lastName;

    @NotNull(message = "Mobile number can't be null.")
    @Pattern(regexp = "^09\\d{9}$" , message = "Invalid mobile number format.")
    private String mobileNumber;

    @Email(message = "Invalid email format.")
    @NotNull
    private String email;

    @NotNull(message = "Date of birth can't be null.")
    private LocalDate dateOfBirth;

    @NotNull(message = "Gender can't be null.")
    private Gender gender;

    private String militaryStatus;

    @Pattern(regexp = "\\d{10}" , message = "National Id must be 10 digits.")
    @NotNull
    private String nationalId;

}