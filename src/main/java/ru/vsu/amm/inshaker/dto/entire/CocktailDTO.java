package ru.vsu.amm.inshaker.dto.entire;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import ru.vsu.amm.inshaker.dto.simple.ItemSimpleDTO;
import ru.vsu.amm.inshaker.dto.simple.MixingMethodDTO;
import ru.vsu.amm.inshaker.dto.simple.TablewareSimpleDTO;
import ru.vsu.amm.inshaker.model.Taste;
import ru.vsu.amm.inshaker.model.cocktail.CocktailGroup;
import ru.vsu.amm.inshaker.model.cocktail.CocktailSubgroup;
import ru.vsu.amm.inshaker.model.enums.Spirit;
import ru.vsu.amm.inshaker.model.item.properties.ItemSubgroup;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.Set;

@Data
public class CocktailDTO {

    private Long id;

    private String nameRu;
    private String nameEn;

    @JsonIgnoreProperties("itemGroup")
    private ItemSubgroup base;

    private CocktailGroup cocktailGroup;
    private CocktailSubgroup cocktailSubgroup;

    @PositiveOrZero
    private Byte spirit;

    private Spirit spiritType;

    private MixingMethodDTO mixingMethod;

    @JsonIgnoreProperties("recipe")
    private TablewareSimpleDTO glass;

    private ItemSimpleDTO garnish;

    private Set<Taste> taste;

    @Valid
    @JsonIgnoreProperties({"cocktail"})
    private Set<RecipePartDTO> recipePart;

    private String legend;

    @URL
    private String sourceRef;

    @URL
    private String imageRef;

    private boolean isFavorite;

}
