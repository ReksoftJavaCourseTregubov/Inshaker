package ru.vsu.amm.inshaker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.vsu.amm.inshaker.model.user.User;

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

    @ManyToOne
    private Ingredient base;
    private String cocktailGroup;
    private String subgroup;
    private byte spirit;

    private String mixingMethod;

    @ManyToOne
    private Ingredient glass;

    @ManyToOne
    private Ingredient garnish;

    @ElementCollection
    private Set<String> taste;

    @JsonIgnoreProperties("cocktail")
    @OneToMany(mappedBy = "cocktail", cascade = CascadeType.ALL)
    private Set<Recipe> recipe;

    private String legend;

    private String sourceRef;

    private String imageRef;

    @ManyToOne
    private User author;

}
