package ru.vsu.amm.inshaker.dto.simple;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ItemSimpleDTO {

    private Long id;
    private String nameRu;

    @NotBlank
    private String dType;

}
