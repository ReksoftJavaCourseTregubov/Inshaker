package ru.vsu.amm.inshaker.exceptions.notfound;

public class CocktailNotFoundException extends RuntimeException {

    public CocktailNotFoundException(Long id) {
        super("Could not find cocktail " + id);
    }

}
