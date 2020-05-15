package ru.vsu.amm.inshaker.model.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.vsu.amm.inshaker.model.RecipePart;
import ru.vsu.amm.inshaker.model.Taste;
import ru.vsu.amm.inshaker.model.item.properties.Country;
import ru.vsu.amm.inshaker.model.item.properties.IngredientBase;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Ingredient extends Item {

    @ManyToOne
    private IngredientBase ingredientBase;

    @PositiveOrZero
    private Byte spirit;

    @ManyToOne
    private Country country;

    @ManyToMany
    @JoinTable(name = "INGREDIENT_TASTE")
    private Set<Taste> taste;

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecipePart> recipePart;

    @Transient
    private boolean inBar;

    @Transient
    private final boolean availableForBar = true;

}
