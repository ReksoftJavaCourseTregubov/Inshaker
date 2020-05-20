package ru.vsu.amm.inshaker.services.factory;

import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.dto.entire.items.ItemDTO;
import ru.vsu.amm.inshaker.model.item.Item;

@Service
public abstract class ItemFactory<T extends Item, S extends ItemDTO> {

    public abstract T createItem();

    public abstract S createItemDTO();

}
