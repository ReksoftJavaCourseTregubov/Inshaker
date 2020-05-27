package ru.vsu.amm.inshaker.dto.entire;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.vsu.amm.inshaker.dto.simple.ItemSimpleDTO;

import javax.validation.constraints.PositiveOrZero;

@Data
public class RecipePartDTO {

    private ItemSimpleDTO ingredient;

    @PositiveOrZero
    @EqualsAndHashCode.Exclude
    private Short amount;

    @EqualsAndHashCode.Exclude
    private boolean isBase;

}
