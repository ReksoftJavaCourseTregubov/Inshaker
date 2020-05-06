package ru.vsu.amm.inshaker.exceptions.notfound;

public class PartyNotFoundException extends RuntimeException {

    public PartyNotFoundException(Long id) {
        super("Could not find party " + id);
    }

}
