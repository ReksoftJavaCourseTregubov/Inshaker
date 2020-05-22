package ru.vsu.amm.inshaker.dto.maps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vsu.amm.inshaker.dto.simple.CocktailSimpleDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CocktailAmountDTO {

    private CocktailSimpleDTO cocktail;

    @NotNull
    @Positive
    private Short amount;

}
