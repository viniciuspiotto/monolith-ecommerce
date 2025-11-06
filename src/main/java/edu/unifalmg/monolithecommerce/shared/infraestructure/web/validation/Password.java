package edu.unifalmg.monolithecommerce.shared.infraestructure.web.validation;

import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {

    String message() default "Password must be at least 8 characters long, with uppercase, lowercase, number, and special character";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
