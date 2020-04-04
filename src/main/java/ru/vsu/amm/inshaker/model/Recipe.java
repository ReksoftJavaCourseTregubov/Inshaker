package ru.vsu.amm.inshaker.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@Entity
public class Recipe implements Serializable {

    @JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    @Id
    @ManyToOne
    @EqualsAndHashCode.Exclude
    private Cocktail cocktail;

    @JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    @Id
    @ManyToOne
    @EqualsAndHashCode.Exclude
    private Ingredient ingredient;

    private Short amount;

}
