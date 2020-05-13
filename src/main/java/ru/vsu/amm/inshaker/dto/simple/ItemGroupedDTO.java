package ru.vsu.amm.inshaker.dto.simple;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemGroupedDTO {

    @NotNull
    private Long groupId;

    private String groupName;

    private List<ItemDTO> items;

}
