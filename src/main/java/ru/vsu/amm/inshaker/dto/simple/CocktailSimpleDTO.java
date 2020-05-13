package ru.vsu.amm.inshaker.dto.simple;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.Valid;
import java.util.List;

@Data
public class CocktailSimpleDTO {

    private Long id;

    private String nameRu;
    private String nameEn;

    @Valid
    @JsonIgnoreProperties({"cocktails"})
    private List<ItemDTO> ingredients;

    @URL
    private String imageRef;

}
