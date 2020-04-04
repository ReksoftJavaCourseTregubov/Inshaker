package ru.vsu.amm.inshaker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class Cocktail {

    @Id
    @GeneratedValue
    private Long id;

    private String nameRu;
    private String nameEn;

    @JsonIgnoreProperties({"ingredientGroup", "country", "ingredientCategory",
            "subgroup", "legend", "taste", "spirit", "recipe", "nameEn", "imageRef", "base"})
    @ManyToOne
    private Ingredient base;
    private String cocktailGroup;
    private String subgroup;
    private byte spirit;

    private String mixingMethod;

    @JsonIgnoreProperties({"ingredientGroup", "country", "ingredientCategory",
            "subgroup", "legend", "taste", "spirit", "recipe", "nameEn", "imageRef", "base"})
    @ManyToOne
    private Ingredient glass;

    @JsonIgnoreProperties({"ingredientGroup", "country", "ingredientCategory",
            "subgroup", "legend", "taste", "spirit", "recipe", "nameEn", "imageRef", "base"})
    @ManyToOne
    private Ingredient garnish;

    @ElementCollection
    private Set<String> taste;

    @OneToMany(mappedBy = "cocktail")
    private Set<Recipe> recipe;

    private String legend;

    private String sourceRef;

    private String imageRef;

}
