package ru.vsu.amm.inshaker.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@Entity
public class Recipe implements Serializable {

    @Id
    @ManyToOne
    @EqualsAndHashCode.Exclude
    private Cocktail cocktail;

    @Id
    @ManyToOne
    @EqualsAndHashCode.Exclude
    private Ingredient ingredient;

    private Short amount;

}
