package ru.vsu.amm.inshaker.dto.maps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vsu.amm.inshaker.dto.simple.CocktailSimpleDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CocktailAmountDTO {

    private CocktailSimpleDTO cocktail;
    private Short amount;

}
