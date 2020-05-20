package ru.vsu.amm.inshaker.dto.simple;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

@Data
public class MixingMethodDTO {

    private Long id;

    @NotBlank
    private String name;

    @URL
    private String iconImageRef;

}
