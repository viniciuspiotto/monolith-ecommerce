package edu.unifalmg.monolithecommerce.shared.infraestructure.web.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AllFilesNotEmptyValidator.class)
public @interface AllFilesNotEmpty {
    String message() default "File list contains an empty or null file.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
