package ru.vsu.amm.inshaker.dto.properties;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.vsu.amm.inshaker.dto.simple.MixingMethodDTO;
import ru.vsu.amm.inshaker.model.Taste;
import ru.vsu.amm.inshaker.model.cocktail.CocktailGroup;
import ru.vsu.amm.inshaker.model.cocktail.CocktailSubgroup;
import ru.vsu.amm.inshaker.model.enums.Spirit;
import ru.vsu.amm.inshaker.model.item.properties.ItemSubgroup;

import java.util.List;

@Data
public class CocktailPropertiesDTO {

    @JsonIgnoreProperties("itemGroup")
    private List<ItemSubgroup> bases;

    private List<CocktailGroup> groups;

    private List<CocktailSubgroup> subgroups;

    private List<Taste> tastes;

    private List<Spirit> spirits;

    private List<MixingMethodDTO> mixingMethods;

}
