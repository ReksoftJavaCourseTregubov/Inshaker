package ru.vsu.amm.inshaker.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
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
    @PositiveOrZero
    private byte spirit;

    private String mixingMethod;

    @JsonIgnoreProperties("recipe")
    private IngredientSimpleDTO glass;

    @JsonIgnoreProperties("recipe")
    private IngredientSimpleDTO garnish;

    private Set<String> taste;

    @Valid
    @JsonIgnoreProperties("cocktail")
    private Set<RecipeDTO> recipe;

    private String legend;

    @URL
    private String sourceRef;

    @URL
    private String imageRef;

    private boolean isFavorite;

}
