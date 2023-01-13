package com.assessment.userapi.validator;


import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

@Service
public class BirthDateConstraintValidator implements ConstraintValidator<BirthDateValidator, LocalDate> {

    @Override
    public void initialize(BirthDateValidator dateValidator) {
    }

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext context) {
        //check if the user is adult
        if (birthDate != null &&
                LocalDate.now().getYear() > birthDate.getYear() &&
                Period.between(birthDate, LocalDate.now()).getYears() >= 18){
            return true;
        }
        return false;
    }
}

