package ru.vsu.amm.inshaker.exceptions.notfound;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("Could not find user " + id);
    }

}
