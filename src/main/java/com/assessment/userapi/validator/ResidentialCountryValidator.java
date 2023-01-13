package com.assessment.userapi.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ResidentialCountryConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ResidentialCountryValidator {
    String message() default "Invalid residentialCountry : Only French residents are allowed to create an account";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
