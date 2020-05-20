package ru.vsu.amm.inshaker.dto.entire.items;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.vsu.amm.inshaker.model.Taste;
import ru.vsu.amm.inshaker.model.item.properties.Country;
import ru.vsu.amm.inshaker.model.item.properties.IngredientBase;

import javax.validation.constraints.PositiveOrZero;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class IngredientDTO extends ItemDTO {

    private IngredientBase ingredientBase;

    @PositiveOrZero
    private Byte spirit;

    private Country country;

    private Set<Taste> taste;

    private boolean inBar;

    private final boolean availableForBar = true;

}
