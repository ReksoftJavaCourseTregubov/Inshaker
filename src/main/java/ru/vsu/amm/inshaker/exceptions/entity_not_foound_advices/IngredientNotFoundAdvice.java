package ru.vsu.amm.inshaker.exceptions.entity_not_foound_advices;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.vsu.amm.inshaker.exceptions.entity_not_foound_exceptions.IngredientNotFoundException;

@ControllerAdvice
public class IngredientNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(IngredientNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String ingredientNotFoundHandler(IngredientNotFoundException ex) {
        return ex.getMessage();
    }

}
