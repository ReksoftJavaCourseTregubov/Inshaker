package ru.vsu.amm.inshaker.model.item;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Garnish extends Ingredient {
}
