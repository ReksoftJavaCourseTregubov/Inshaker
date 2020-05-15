package ru.vsu.amm.inshaker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipePartId implements Serializable {

    private Long cocktail;
    private Long ingredient;

}
