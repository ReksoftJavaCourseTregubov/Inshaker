package ru.vsu.amm.inshaker.exceptions;

public class AnonymousAuthenticationException extends RuntimeException {

    public AnonymousAuthenticationException(String details) {
        super("Anonymous authentication: " + details);
    }

}
