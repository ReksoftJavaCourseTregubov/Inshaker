package ru.vsu.amm.inshaker.model.cocktail;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class MixingMethodStep {

    @Id
    @GeneratedValue
    private Long id;

    private String description;

    @URL
    private String imageRef;

}
