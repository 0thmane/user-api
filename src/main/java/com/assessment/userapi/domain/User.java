package com.assessment.userapi.domain;


import com.assessment.userapi.validator.BirthDateValidator;
import com.assessment.userapi.validator.ResidentialCountryValidator;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "app_user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractAuditingEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Invalid username : username is null")
    @NotEmpty(message = "Invalid username : username is empty")
    private String username;

    @NotNull(message = "Invalid birthDate : birthDate is null")
    @BirthDateValidator(message = "Invalid birthDate : Only adult are allowed to create an account (+18)")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Invalid residentialCountry : residentialCountry is null")
    @ResidentialCountryValidator
    private ResidentialCountry residentialCountry;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Gender gender;
}
