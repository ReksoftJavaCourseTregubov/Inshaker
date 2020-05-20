package ru.vsu.amm.inshaker.dto.maps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vsu.amm.inshaker.dto.simple.ItemSimpleDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientAmountDTO {

    private ItemSimpleDTO ingredient;
    private Short amount;

}
