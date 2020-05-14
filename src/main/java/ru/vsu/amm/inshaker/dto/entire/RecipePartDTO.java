package ru.vsu.amm.inshaker.dto.entire;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.vsu.amm.inshaker.dto.simple.ItemDTO;

import javax.validation.constraints.PositiveOrZero;

@Data
public class RecipePartDTO {

    @JsonIgnoreProperties({"cocktails", "nameEn", "imageRef"})
    private ItemDTO ingredient;

    @PositiveOrZero
    @EqualsAndHashCode.Exclude
    private Short amount;

}
