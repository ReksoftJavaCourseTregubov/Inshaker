package ru.vsu.amm.inshaker.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

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
    private byte spirit;
    private String cocktailGroup;
    private String subgroup;

    @ElementCollection
    private List<String> taste;

    @ElementCollection
    private Map<Ingredient, Float> ingredientCount;

//    Serve

    private String legend;

    private String sourceRef;

    private String imageRef;

}
