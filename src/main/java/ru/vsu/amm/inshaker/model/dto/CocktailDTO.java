package ru.vsu.amm.inshaker.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Set;

@Data
public class CocktailDTO {

    private Long id;

    private String nameRu;
    private String nameEn;

    @JsonIgnoreProperties("recipe")
    private IngredientSimpleDTO base;

    private String cocktailGroup;
    private String subgroup;
    private byte spirit;

    private String mixingMethod;

    @JsonIgnoreProperties("recipe")
    private IngredientSimpleDTO glass;

    @JsonIgnoreProperties("recipe")
    private IngredientSimpleDTO garnish;

    private Set<String> taste;

    @JsonIgnoreProperties("cocktail")
    private Set<RecipeDTO> recipe;

    private String legend;

    private String sourceRef;

    private String imageRef;

    private boolean isFavorite;

}
