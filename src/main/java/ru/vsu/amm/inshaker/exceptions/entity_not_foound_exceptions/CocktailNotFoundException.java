package ru.vsu.amm.inshaker.exceptions.entity_not_foound_exceptions;

public class CocktailNotFoundException extends RuntimeException {

    public CocktailNotFoundException(Long id) {
        super("Could not find cocktail " + id);
    }

}
