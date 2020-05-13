package ru.vsu.amm.inshaker.exceptions;

public class PartyAccessDeniedException extends RuntimeException  {

    public PartyAccessDeniedException(String details) {
        super("Party access denied: " + details);
    }

}
