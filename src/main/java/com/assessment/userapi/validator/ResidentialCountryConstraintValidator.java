package com.assessment.userapi.validator;


import com.assessment.userapi.domain.ResidentialCountry;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class ResidentialCountryConstraintValidator implements ConstraintValidator<ResidentialCountryValidator, ResidentialCountry> {

    private final static List<ResidentialCountry> ALLOWED_RESIDENTIAL_COUNTRY = List.of(ResidentialCountry.FR);

    @Override
    public void initialize(ResidentialCountryValidator dateValidator) {
    }

    @Override
    public boolean isValid(ResidentialCountry residentialCountry, ConstraintValidatorContext context) {
        //check if the received residential country is allowed
        if (residentialCountry != null && isAllowedResidentialCountry(residentialCountry)){
            return true;
        }
        return false;
    }

    /**
     * This method check if the received residential country is allowed or not
     * @param residentialCountry
     * @return boolean
     */
    private boolean isAllowedResidentialCountry(ResidentialCountry residentialCountry){
        return ALLOWED_RESIDENTIAL_COUNTRY.contains(residentialCountry);
    }
}

