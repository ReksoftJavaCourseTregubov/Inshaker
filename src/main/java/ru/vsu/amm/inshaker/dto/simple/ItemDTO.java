package ru.vsu.amm.inshaker.dto.simple;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.Valid;
import java.util.List;

@Data
public class ItemDTO {

    private Long id;

    private String nameRu;
    private String nameEn;

    @Valid
    @JsonIgnoreProperties("ingredients")
    private List<CocktailSimpleDTO> cocktails;

    @URL
    private String imageRef;

}
