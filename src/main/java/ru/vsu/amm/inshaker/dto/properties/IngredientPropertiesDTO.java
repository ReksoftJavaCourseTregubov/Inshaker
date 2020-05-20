package ru.vsu.amm.inshaker.dto.properties;

import lombok.Data;
import ru.vsu.amm.inshaker.model.Taste;
import ru.vsu.amm.inshaker.model.enums.Spirit;
import ru.vsu.amm.inshaker.model.item.properties.Country;
import ru.vsu.amm.inshaker.model.item.properties.IngredientBase;
import ru.vsu.amm.inshaker.model.item.properties.ItemSubgroup;

import java.util.List;

@Data
public class IngredientPropertiesDTO {

    private List<ItemSubgroup> subGroups;

    private List<Taste> tastes;

    private List<IngredientBase> bases;

    private List<Spirit> spirits;

    private List<Country> countries;

}
