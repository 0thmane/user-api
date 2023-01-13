package com.assessment.userapi.dto;

import com.assessment.userapi.domain.Gender;
import com.assessment.userapi.domain.ResidentialCountry;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String username;
    private LocalDate birthDate;
    private ResidentialCountry residentialCountry;
    private String phone;
    private Gender gender;
    private Instant createdDate;
    private Instant lastModifiedDate;

    @Override
    public String toString() {
        return String.format("UserDTO [id=%d, username=%s, birthDate=%s, residentialCountry=%s, phone=%s, gender=%s", id, username, birthDate, residentialCountry, phone, gender);
    }
}
