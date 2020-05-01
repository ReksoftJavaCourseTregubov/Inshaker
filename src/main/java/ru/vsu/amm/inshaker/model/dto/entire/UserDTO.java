package ru.vsu.amm.inshaker.model.dto.entire;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserDTO {

    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String passwordConfirm;

}
