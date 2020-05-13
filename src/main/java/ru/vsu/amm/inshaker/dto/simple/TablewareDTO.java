package ru.vsu.amm.inshaker.dto.simple;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class TablewareDTO {

    private Long id;

    private String nameRu;

    @URL
    private String iconImageRef;

}
