package ru.vsu.amm.inshaker.model.dto.maps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vsu.amm.inshaker.model.dto.simple.CocktailSimpleDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CocktailAmountDTO {

    @JsonIgnoreProperties("recipe")
    private CocktailSimpleDTO cocktail;
    private Short amount;

}
