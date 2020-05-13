package ru.vsu.amm.inshaker.exceptions.advices;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.vsu.amm.inshaker.exceptions.PartyAccessDeniedException;

@ControllerAdvice
public class AccessDeniedAdvice {

    @ResponseBody
    @ExceptionHandler(PartyAccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String accessDeniedHandler(PartyAccessDeniedException ex) {
        return ex.getMessage();
    }

}
