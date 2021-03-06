package ru.vsu.amm.inshaker.dto.simple;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
public class PartySimpleDTO {

    @NotNull
    private Long id;

    private String name;

    @PositiveOrZero
    private Short guestsCount;

    @PositiveOrZero
    private Short cocktailsCount;

    private UserSimpleDTO author;

}
