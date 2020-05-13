package ru.vsu.amm.inshaker.model.cocktail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import ru.vsu.amm.inshaker.model.RecipePart;
import ru.vsu.amm.inshaker.model.Taste;
import ru.vsu.amm.inshaker.model.item.Garnish;
import ru.vsu.amm.inshaker.model.item.Tableware;
import ru.vsu.amm.inshaker.model.user.User;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.util.Set;

@Data
@Entity
public class Cocktail {

    @Id
    @GeneratedValue
    private Long id;

    private String nameRu;
    private String nameEn;

    @ManyToOne
    private CocktailGroup cocktailGroup;

    @ManyToOne
    private CocktailSubgroup cocktailSubgroup;

    @PositiveOrZero
    private byte spirit;

    @ManyToOne
    private MixingMethod mixingMethod;

    @ManyToOne
    private Tableware glass;

    @ManyToOne
    private Garnish garnish;

    @ManyToMany
    private Set<Taste> taste;

    @JsonIgnoreProperties("cocktail")
    @OneToMany(mappedBy = "cocktail", cascade = CascadeType.ALL)
    private Set<RecipePart> recipePart;

    private String legend;

    @URL
    private String sourceRef;

    @URL
    private String imageRef;

    @ManyToOne
    private User author;

}
