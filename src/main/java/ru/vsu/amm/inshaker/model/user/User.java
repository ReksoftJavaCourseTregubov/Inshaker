package ru.vsu.amm.inshaker.model.user;

import lombok.Data;
import ru.vsu.amm.inshaker.model.Cocktail;
import ru.vsu.amm.inshaker.model.Ingredient;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String password;

    @Transient
    private String passwordConfirm;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Role> roles;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Cocktail> favorite;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Ingredient> bar;

}

// https://hellokoding.com/registration-and-login-example-with-spring-security-spring-boot-spring-data-jpa-hsql-jsp/