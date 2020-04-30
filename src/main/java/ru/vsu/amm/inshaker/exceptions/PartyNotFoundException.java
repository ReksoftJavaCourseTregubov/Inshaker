package ru.vsu.amm.inshaker.exceptions;

public class PartyNotFoundException extends RuntimeException {

    public PartyNotFoundException(Long id) {
        super("Could not find party " + id);
    }

}
