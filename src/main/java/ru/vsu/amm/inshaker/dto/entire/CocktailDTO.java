package ru.vsu.amm.inshaker.dto.entire;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import ru.vsu.amm.inshaker.dto.simple.ItemDTO;
import ru.vsu.amm.inshaker.dto.simple.TablewareDTO;
import ru.vsu.amm.inshaker.model.Taste;
import ru.vsu.amm.inshaker.model.cocktail.CocktailGroup;
import ru.vsu.amm.inshaker.model.cocktail.CocktailSubgroup;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.Set;

@Data
public class CocktailDTO {

    private Long id;

    private String nameRu;
    private String nameEn;

    @JsonIgnoreProperties({"cocktails", "nameEn", "imageRef"})
    private ItemDTO base;

    private CocktailGroup cocktailGroup;
    private CocktailSubgroup cocktailSubgroup;

    @PositiveOrZero
    private byte spirit;

    private MixingMethodDTO mixingMethod;

    @JsonIgnoreProperties("recipe")
    private TablewareDTO glass;

    @JsonIgnoreProperties({"cocktails", "nameEn", "imageRef"})
    private ItemDTO garnish;

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
