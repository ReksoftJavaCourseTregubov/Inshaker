package ru.vsu.amm.inshaker.services.factory;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.dto.entire.items.ItemDTO;
import ru.vsu.amm.inshaker.model.item.Item;

@Service
@Primary
public class PrimaryItemFactory extends ItemFactory<Item, ItemDTO> {

    @Override
    public Item createItem() {
        return new Item();
    }

    @Override
    public ItemDTO createItemDTO() {
        return new ItemDTO();
    }

}
