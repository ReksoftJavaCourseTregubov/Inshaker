package ru.vsu.amm.inshaker.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class RecipeDTO {

    @JsonIgnoreProperties("recipe")
    @EqualsAndHashCode.Exclude
    private CocktailSimpleDTO cocktail;

    @JsonIgnoreProperties("recipe")
    @EqualsAndHashCode.Exclude
    private IngredientSimpleDTO ingredient;

    private Short amount;

}