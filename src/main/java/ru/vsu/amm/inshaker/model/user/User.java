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

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Role> roles;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Cocktail> favorite;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Ingredient> bar;

}

// https://hellokoding.com/registration-and-login-example-with-spring-security-spring-boot-spring-data-jpa-hsql-jsp/