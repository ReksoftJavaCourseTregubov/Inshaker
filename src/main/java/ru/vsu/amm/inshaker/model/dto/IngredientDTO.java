package ru.vsu.amm.inshaker.model.dto;

import lombok.Data;

import java.util.Set;

@Data
public class IngredientDTO {

    private Long id;

    private String nameRu;
    private String nameEn;

    private String ingredientCategory;
    private String ingredientGroup;
    private String subgroup;

    private String base;
    private Byte spirit;
    private String country;

    private Set<String> taste;

    private String legend;

    private String imageRef;

}
