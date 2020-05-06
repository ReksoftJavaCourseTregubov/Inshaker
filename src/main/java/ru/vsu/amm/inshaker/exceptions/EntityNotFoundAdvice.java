package ru.vsu.amm.inshaker.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.vsu.amm.inshaker.exceptions.notfound.CocktailNotFoundException;
import ru.vsu.amm.inshaker.exceptions.notfound.IngredientNotFoundException;
import ru.vsu.amm.inshaker.exceptions.notfound.PartyNotFoundException;
import ru.vsu.amm.inshaker.exceptions.notfound.UserNotFoundException;

@ControllerAdvice
public class EntityNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler({
            CocktailNotFoundException.class,
            IngredientNotFoundException.class,
            PartyNotFoundException.class,
            UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String entityNotFoundAdvice(RuntimeException ex) {
        return ex.getMessage();
    }

}
