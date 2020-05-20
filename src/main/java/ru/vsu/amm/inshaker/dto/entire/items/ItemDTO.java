package ru.vsu.amm.inshaker.dto.entire.items;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import ru.vsu.amm.inshaker.dto.simple.CocktailSimpleDTO;
import ru.vsu.amm.inshaker.model.item.properties.ItemSubgroup;

import javax.validation.Valid;
import java.util.List;

@Data
public class ItemDTO {

    private Long id;

    private String nameRu;
    private String nameEn;

    private ItemSubgroup itemSubgroup;

    @Valid
    @JsonIgnoreProperties("ingredients")
    private List<CocktailSimpleDTO> cocktails;

    private String legend;

    @URL
    private String imageRef;

    private String dType;

}
