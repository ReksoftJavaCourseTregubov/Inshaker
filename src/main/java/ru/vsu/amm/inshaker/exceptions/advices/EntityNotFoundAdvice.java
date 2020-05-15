package ru.vsu.amm.inshaker.exceptions.advices;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.vsu.amm.inshaker.exceptions.notfound.*;

@ControllerAdvice
public class EntityNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler({
            CocktailNotFoundException.class,
            ItemNotFoundException.class,
            PartyNotFoundException.class,
            UserNotFoundException.class,
            EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String entityNotFoundAdvice(RuntimeException ex) {
        return ex.getMessage();
    }

}
