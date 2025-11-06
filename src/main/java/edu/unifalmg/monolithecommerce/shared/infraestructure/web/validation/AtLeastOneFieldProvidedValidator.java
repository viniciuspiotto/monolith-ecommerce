package edu.unifalmg.monolithecommerce.shared.infraestructure.web.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Method;
import java.util.Arrays;

public class AtLeastOneFieldProvidedValidator
        implements ConstraintValidator<AtLeastOneFieldProvided, Record> {

    @Override
    public boolean isValid(Record value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return Arrays.stream(value.getClass().getRecordComponents())
                .anyMatch(component -> {
                    try {
                        Method accessor = component.getAccessor();
                        Object fieldValue = accessor.invoke(value);
                        return fieldValue != null;
                    } catch (Exception e) {
                        return false;
                    }
                });
    }
}
