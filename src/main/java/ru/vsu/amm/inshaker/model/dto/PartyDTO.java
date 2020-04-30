package ru.vsu.amm.inshaker.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

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

    @JsonIgnoreProperties("recipe")
    private List<@Valid CocktailSimpleDTO> cocktailAmount;

    private String legend;

    private UserDTO author;

    private Set<@Valid UserDTO> members;

}
