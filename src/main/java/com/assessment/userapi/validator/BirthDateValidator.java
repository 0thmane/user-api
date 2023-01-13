package com.assessment.userapi.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
@Documented
@Constraint(validatedBy = BirthDateConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface BirthDateValidator  {
    String message() default "Invalid birthDate";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
