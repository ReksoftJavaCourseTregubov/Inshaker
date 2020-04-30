package ru.vsu.amm.inshaker.exceptions.advices;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.vsu.amm.inshaker.exceptions.PartyNotFoundException;

@ControllerAdvice
public class PartyNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(PartyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String partyNotFoundAdvice(PartyNotFoundException ex) {
        return ex.getMessage();
    }

}
