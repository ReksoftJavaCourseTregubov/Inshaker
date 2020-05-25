package ru.vsu.amm.inshaker.model.user;

import lombok.Data;
import ru.vsu.amm.inshaker.model.cocktail.Cocktail;
import ru.vsu.amm.inshaker.model.item.Ingredient;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @ManyToMany
    private Set<Role> roles;

    @ManyToMany
    private Set<Cocktail> favorite;

    @ManyToMany
    private Set<Ingredient> bar;

}
