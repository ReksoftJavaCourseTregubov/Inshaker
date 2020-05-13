package ru.vsu.amm.inshaker.model.item.properties;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Data
@Entity
public class ItemCategory {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String name;

}
