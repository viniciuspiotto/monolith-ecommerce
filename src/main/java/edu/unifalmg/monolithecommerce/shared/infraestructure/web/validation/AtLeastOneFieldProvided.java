package edu.unifalmg.monolithecommerce.shared.infraestructure.web.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AtLeastOneFieldProvidedValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface AtLeastOneFieldProvided {

    String message() default "At least one field must be provided for this operation.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
