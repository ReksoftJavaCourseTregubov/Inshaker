package ru.vsu.amm.inshaker.services.factory;

import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.dto.entire.items.GarnishDTO;
import ru.vsu.amm.inshaker.model.item.Garnish;

@Service
public class GarnishFactory extends ItemFactory<Garnish, GarnishDTO>{

    @Override
    public Garnish createItem() {
        return new Garnish();
    }

    @Override
    public GarnishDTO createItemDTO() {
        return new GarnishDTO();
    }

}
