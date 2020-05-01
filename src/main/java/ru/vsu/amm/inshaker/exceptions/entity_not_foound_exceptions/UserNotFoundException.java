package ru.vsu.amm.inshaker.exceptions.entity_not_foound_exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("Could not find user " + id);
    }

}
