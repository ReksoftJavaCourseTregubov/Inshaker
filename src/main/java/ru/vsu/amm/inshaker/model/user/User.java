package ru.vsu.amm.inshaker.model.user;

import lombok.Data;
import ru.vsu.amm.inshaker.model.Cocktail;

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

    @ManyToMany
    private Set<Role> roles;

    @ManyToMany
    private Set<Cocktail> favorite;

}
