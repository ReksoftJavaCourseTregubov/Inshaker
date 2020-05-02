package ru.vsu.amm.inshaker.model.dto.entire;

import lombok.Data;
import ru.vsu.amm.inshaker.model.dto.maps.CocktailAmountDTO;
import ru.vsu.amm.inshaker.model.dto.maps.IngredientAmountDTO;

import javax.validation.constraints.PositiveOrZero;
import java.util.Set;

@Data
public class PartyDTO {

    private Long id;

    private String name;

    @PositiveOrZero
    private Short guestsCount;

    private Set<CocktailAmountDTO> cocktailAmount;

    private Set<IngredientAmountDTO> ingredientAmount;

    private String legend;

    private UserDTO author;

    private Set<UserDTO> members;

}
