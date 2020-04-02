package ru.vsu.amm.inshaker.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Ingredient {

    @Id
    @GeneratedValue
    private Long id;

    private String nameRu;
    private String nameEn;

    private String ingredientCategory;
    private String ingredientGroup;
    private String subgroup;

    private String base;
    private Byte spirit;
    private String country;

    @ElementCollection
    private List<String> taste;

    private String legend;

    private String imageRef;

}
