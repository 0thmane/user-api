package com.assessment.userapi.dto;

import com.assessment.userapi.domain.Gender;
import com.assessment.userapi.domain.ResidentialCountry;
import com.assessment.userapi.validator.BirthDateValidator;
import com.assessment.userapi.validator.ResidentialCountryValidator;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.time.LocalDate;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationDTO {
    @NotNull(message = "Invalid username : username is null")
    @NotEmpty(message = "Invalid username : username is empty")
    private String username;
    @NotNull(message = "Invalid birthDate : birthDate is null")
    @BirthDateValidator(message = "Invalid birthDate : Only adult are allowed to create an account (+18)")
    private LocalDate birthDate;
    @NotNull(message = "Invalid residentialCountry : residentialCountry is null")
    @ResidentialCountryValidator
    private ResidentialCountry residentialCountry;

    @Pattern(regexp = "^(?:(?:(?:\\+|00)33[ ]?(?:\\(0\\)[ ]?)?)|0){1}[1-9]{1}([ .-]?)(?:\\d{2}\\1?){3}\\d{2}$", message = "Invalid phone")
    private String phone;
    private Gender gender;

    @Override
    public String toString() {
        return String.format("UserCreationDTO [username=%s, birthDate=%s, residentialCountry=%s, phone=%s, gender=%s", username, birthDate, residentialCountry, phone, gender);
    }

}
