package ru.vsu.amm.inshaker.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Set;

@Data
public class CocktailSimpleDTO {

    private Long id;

    private String nameRu;
    private String nameEn;

    @JsonIgnoreProperties("cocktail")
    private Set<RecipeDTO> recipe;

    private String imageRef;

}
