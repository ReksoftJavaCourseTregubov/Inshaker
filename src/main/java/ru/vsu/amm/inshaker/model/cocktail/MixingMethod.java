package ru.vsu.amm.inshaker.model.cocktail;

import lombok.Data;
import org.hibernate.validator.constraints.URL;
import ru.vsu.amm.inshaker.model.item.Tableware;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@Entity
public class MixingMethod {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String name;

    @ManyToMany
    private Set<Tableware> tableware;

    private String description;

    @URL
    private String iconImageRef;

}
