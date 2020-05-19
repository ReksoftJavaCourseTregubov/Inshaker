package ru.vsu.amm.inshaker.dto.properties;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.vsu.amm.inshaker.model.Taste;
import ru.vsu.amm.inshaker.model.enums.Spirit;
import ru.vsu.amm.inshaker.model.item.properties.*;

import java.util.List;

@Data
public class IngredientPropertiesDTO {

    @JsonIgnoreProperties("itemGroup")
    private List<ItemSubgroup> subGroups;

    @JsonIgnoreProperties("itemCategory")
    private List<ItemGroup> groups;

    private List<ItemCategory> categories;

    private List<Taste> tastes;

    private List<IngredientBase> bases;

    private List<Spirit> spirits;

    @JsonIgnoreProperties("flagImageRef")
    private List<Country> countries;

}
