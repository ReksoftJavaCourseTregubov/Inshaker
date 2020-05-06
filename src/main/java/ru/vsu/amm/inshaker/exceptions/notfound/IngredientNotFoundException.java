package ru.vsu.amm.inshaker.exceptions.notfound;

public class IngredientNotFoundException extends RuntimeException {

    public IngredientNotFoundException(Long id) {
        super("Could not find ingredient " + id);
    }

}
