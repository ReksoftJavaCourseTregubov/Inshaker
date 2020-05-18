package ru.vsu.amm.inshaker.model.cocktail;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import ru.vsu.amm.inshaker.model.item.Tableware;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class MixingMethod {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String name;

    @JsonIgnore
    @ManyToMany
    private Set<Tableware> tableware;

    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    private List<MixingMethodStep> mixingMethodStep;

    @URL
    private String iconImageRef;

}
