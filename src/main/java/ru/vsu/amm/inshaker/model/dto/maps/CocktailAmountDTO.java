package ru.vsu.amm.inshaker.model.dto.maps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.vsu.amm.inshaker.model.dto.simple.CocktailSimpleDTO;

@Data
public class CocktailAmountDTO {

    @JsonIgnoreProperties("recipe")
    private CocktailSimpleDTO cocktail;
    private Short amount;

    public CocktailAmountDTO() {
    }

    public CocktailAmountDTO(CocktailSimpleDTO cocktail, Short amount) {
        this.cocktail = cocktail;
        this.amount = amount;
    }

}
