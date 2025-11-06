package edu.unifalmg.monolithecommerce.shared.infraestructure.web.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class AllFilesNotEmptyValidator implements ConstraintValidator<AllFilesNotEmpty, List<MultipartFile>> {

    @Override
    public void initialize(AllFilesNotEmpty constraintAnnotation) {}

    @Override
    public boolean isValid(List<MultipartFile> files, ConstraintValidatorContext context) {
        if (files == null || files.isEmpty()) {
            return true;
        }

        for (MultipartFile file : files) {
            if (file == null || file.isEmpty()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                        "File cannot be empty."
                ).addConstraintViolation();
                return false;
            }
        }

        return true;
    }
}
