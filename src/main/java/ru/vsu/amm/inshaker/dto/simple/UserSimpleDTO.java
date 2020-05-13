package ru.vsu.amm.inshaker.dto.simple;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserSimpleDTO {

    private Long id;

    @NotBlank
    private String username;

}
