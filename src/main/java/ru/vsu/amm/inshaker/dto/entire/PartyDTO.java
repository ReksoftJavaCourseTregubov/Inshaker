package ru.vsu.amm.inshaker.dto.entire;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.vsu.amm.inshaker.dto.maps.CocktailAmountDTO;
import ru.vsu.amm.inshaker.dto.maps.IngredientAmountDTO;
import ru.vsu.amm.inshaker.dto.simple.TablewareSimpleDTO;
import ru.vsu.amm.inshaker.dto.simple.UserSimpleDTO;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Set;

@Data
public class PartyDTO {

    private Long id;

    private String name;

    @PositiveOrZero
    private Short guestsCount;

    @Valid
    private List<CocktailAmountDTO> cocktailAmount;

    @JsonProperty("recipePart")
    private List<IngredientAmountDTO> ingredientAmount;

    private Set<TablewareSimpleDTO> tableware;

    private String legend;

    private UserSimpleDTO author;

    private Set<UserSimpleDTO> members;

}
