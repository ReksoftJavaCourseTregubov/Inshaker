package ru.vsu.amm.inshaker.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.Valid;
import java.util.Set;

@Data
public class IngredientSimpleDTO {

    private Long id;

    private String nameRu;
    private String nameEn;

    @Valid
    @JsonIgnoreProperties("ingredient")
    private Set<RecipeDTO> recipe;

    @URL
    private String imageRef;

}
