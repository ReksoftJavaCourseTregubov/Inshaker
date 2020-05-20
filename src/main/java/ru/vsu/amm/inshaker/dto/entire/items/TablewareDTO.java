package ru.vsu.amm.inshaker.dto.entire.items;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TablewareDTO extends ItemDTO {

    private String iconImageRef;

}
