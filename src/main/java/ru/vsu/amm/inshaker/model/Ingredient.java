package ru.vsu.amm.inshaker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
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
    @PositiveOrZero
    private Byte spirit;
    private String country;

    @ElementCollection
    private Set<String> taste;

    @Valid
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL)
    private Set<Recipe> recipe;

    private String legend;

    @URL
    private String imageRef;

}
