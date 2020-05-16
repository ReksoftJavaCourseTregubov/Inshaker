package ru.vsu.amm.inshaker.exceptions;

public class NotBlankException extends RuntimeException {

    public NotBlankException(String details) {
        super("Property must be not blank: " + details);
    }

}
