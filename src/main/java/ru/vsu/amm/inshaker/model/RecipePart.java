package ru.vsu.amm.inshaker.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.vsu.amm.inshaker.model.cocktail.Cocktail;
import ru.vsu.amm.inshaker.model.item.Ingredient;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

@Data
@Entity
public class RecipePart implements Serializable {

    @Id
    @ManyToOne
    @EqualsAndHashCode.Exclude
    private Cocktail cocktail;

    @Id
    @ManyToOne
    private Ingredient ingredient;

    @PositiveOrZero
    private Short amount;

    private Boolean isBase;

}
