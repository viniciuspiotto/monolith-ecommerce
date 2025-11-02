package edu.unifalmg.monolithecommerce.shared.infraestructure.config;

import edu.unifalmg.monolithecommerce.shared.infraestructure.dto.ErrorResponse;
import edu.unifalmg.monolithecommerce.shared.infraestructure.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String key = (error instanceof FieldError) ? ((FieldError) error).getField() : error.getObjectName();
            String errorMessage = error.getDefaultMessage();
            errors.put(key, errorMessage);
        });

        return new ErrorResponse("Validation failed. Please check the fields.", errors);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ErrorResponse handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        String provided = (ex.getContentType() != null) ? ex.getContentType().toString() : "N/A";

        Map<String, String> details = Map.of(
                "provided_content_type", provided,
                "supported_content_types", ex.getSupportedMediaTypes().toString()
        );

        return new ErrorResponse("Media type not supported.", details);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException e) {
        ErrorResponse body = new ErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {

        ErrorResponse body = new ErrorResponse("An unexpected internal server error occurred.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String paramName = ex.getName();
        assert ex.getRequiredType() != null;
        String requiredType = ex.getRequiredType().getSimpleName();
        Object invalidValue = ex.getValue();

        String message = String.format(
                "Parameter '%s' is invalid. The value '%s' don't must be converted to '%s'.",
                paramName, invalidValue, requiredType
        );

        return new ErrorResponse(message);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String specificMessage = ex.getMostSpecificCause().getMessage();

        if (specificMessage != null && specificMessage.contains("Cannot coerce empty String")) {
            return new ErrorResponse("Invalid data format. Empty strings are not allowed for object fields.");
        }

        return new ErrorResponse("JSON parse error: The request body is malformed or unreadable.");
    }
}
