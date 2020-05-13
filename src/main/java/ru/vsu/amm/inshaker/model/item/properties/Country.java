package ru.vsu.amm.inshaker.model.item.properties;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Data
@Entity
public class Country {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String name;

    @URL
    private String FlagImageRef;

}
