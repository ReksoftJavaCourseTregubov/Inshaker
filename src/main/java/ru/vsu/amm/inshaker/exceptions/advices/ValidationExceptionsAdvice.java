package ru.vsu.amm.inshaker.exceptions.advices;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.vsu.amm.inshaker.exceptions.NotBlankException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationExceptionsAdvice {

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> validationExceptionsHandler(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseBody
    @ExceptionHandler(NotBlankException.class)
    public String validationExceptionsHandler(NotBlankException ex) {
        return ex.getMessage();
    }

}
