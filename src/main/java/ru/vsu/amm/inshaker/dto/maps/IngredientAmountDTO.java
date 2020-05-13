package ru.vsu.amm.inshaker.dto.maps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vsu.amm.inshaker.dto.simple.ItemDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientAmountDTO {

    @JsonIgnoreProperties({"cocktails", "nameEn", "imageRef"})
    private ItemDTO ingredient;
    private Short amount;

}
