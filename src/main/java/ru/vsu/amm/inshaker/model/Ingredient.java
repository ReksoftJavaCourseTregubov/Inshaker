package ru.vsu.amm.inshaker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

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
    private Set<String> taste;

    @JsonIgnore
    @OneToMany(mappedBy = "ingredient")
    private Set<Recipe> recipe;

    private String legend;

    private String imageRef;

}
