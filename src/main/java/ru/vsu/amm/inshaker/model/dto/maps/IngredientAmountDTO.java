package ru.vsu.amm.inshaker.model.dto.maps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vsu.amm.inshaker.model.dto.simple.IngredientSimpleDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientAmountDTO {

    @JsonIgnoreProperties("recipe")
    private IngredientSimpleDTO ingredient;
    private Short amount;

}
