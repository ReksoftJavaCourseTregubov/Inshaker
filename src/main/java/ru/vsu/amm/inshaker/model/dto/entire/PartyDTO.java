package ru.vsu.amm.inshaker.model.dto.entire;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.vsu.amm.inshaker.model.dto.simple.CocktailSimpleDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Set;

@Data
public class PartyDTO {

    private Long id;

    private String name;

    @PositiveOrZero
    private Short guestsCount;

    @JsonIgnoreProperties("recipe")
    private List<@Valid @NotNull CocktailSimpleDTO> cocktailAmount;

    private String legend;

    private UserDTO author;

    private Set<@Valid UserDTO> members;

}
