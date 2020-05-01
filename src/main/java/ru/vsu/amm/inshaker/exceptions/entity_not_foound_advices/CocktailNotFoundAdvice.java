package ru.vsu.amm.inshaker.exceptions.entity_not_foound_advices;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.vsu.amm.inshaker.exceptions.entity_not_foound_exceptions.CocktailNotFoundException;

@ControllerAdvice
public class CocktailNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(CocktailNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String cocktailNotFoundHandler(CocktailNotFoundException ex) {
        return ex.getMessage();
    }

}
