package ru.vsu.amm.inshaker.model.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@Entity
public class Role {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String name;

    @ManyToMany(mappedBy = "roles")
    @EqualsAndHashCode.Exclude
    private Set<User> users;

}
