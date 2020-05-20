package ru.vsu.amm.inshaker.dto.entire;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vsu.amm.inshaker.dto.entire.items.ItemDTO;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemGroupedDTO {

    @NotNull
    private Long groupId;

    private String groupName;

    @JsonIgnoreProperties({"itemSubgroup", "legend"})
    private List<ItemDTO> items;

}
