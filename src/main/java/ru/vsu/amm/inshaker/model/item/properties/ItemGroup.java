package ru.vsu.amm.inshaker.model.item.properties;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
public class ItemGroup {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String name;

    @ManyToOne
    private ItemCategory itemCategory;

}
