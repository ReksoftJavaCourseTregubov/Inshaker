package ru.vsu.amm.inshaker.model.dto.maps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.vsu.amm.inshaker.model.dto.simple.IngredientSimpleDTO;

@Data
public class IngredientAmountDTO {

    @JsonIgnoreProperties("recipe")
    private IngredientSimpleDTO ingredient;
    private Short amount;

    public IngredientAmountDTO() {
    }

    public IngredientAmountDTO(IngredientSimpleDTO ingredient, Short amount) {
        this.ingredient = ingredient;
        this.amount = amount;
    }

}
