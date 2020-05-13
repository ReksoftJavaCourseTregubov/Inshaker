package ru.vsu.amm.inshaker.model.item;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Entity;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Tableware extends Item {

    @URL
    private String iconImageRef;

}
