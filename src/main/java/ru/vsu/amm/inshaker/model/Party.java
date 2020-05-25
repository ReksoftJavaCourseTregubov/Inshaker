package ru.vsu.amm.inshaker.model;

import lombok.Data;
import ru.vsu.amm.inshaker.model.cocktail.Cocktail;
import ru.vsu.amm.inshaker.model.user.User;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Map;
import java.util.Set;

@Data
@Entity
public class Party {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @PositiveOrZero
    private Short guestsCount;

    @ElementCollection
    @MapKeyJoinColumn(name = "cocktail_id")
    private Map<Cocktail, @Positive Short> cocktailAmount;

    private String legend;

    @ManyToOne
    private User author;

    @ManyToMany
    private Set<User> members;

}
