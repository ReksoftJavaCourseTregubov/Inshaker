package ru.vsu.amm.inshaker.dto.properties;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.vsu.amm.inshaker.model.Taste;
import ru.vsu.amm.inshaker.model.enums.Spirit;
import ru.vsu.amm.inshaker.model.item.properties.ItemGroup;

import java.util.List;

@Data
public class IngredientPropertiesDTO {

    @JsonIgnoreProperties("itemCategory")
    private List<ItemGroup> groups;

    private List<Taste> tastes;

    private List<Spirit> spirits;

}
