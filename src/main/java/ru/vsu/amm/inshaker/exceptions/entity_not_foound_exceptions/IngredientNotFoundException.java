package ru.vsu.amm.inshaker.exceptions.entity_not_foound_exceptions;

public class IngredientNotFoundException extends RuntimeException {

    public IngredientNotFoundException(Long id) {
        super("Could not find ingredient " + id);
    }

}
