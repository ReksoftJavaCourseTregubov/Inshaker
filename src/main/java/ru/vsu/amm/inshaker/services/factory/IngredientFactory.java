package ru.vsu.amm.inshaker.services.factory;

import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.dto.entire.items.IngredientDTO;
import ru.vsu.amm.inshaker.model.item.Ingredient;

@Service
public class IngredientFactory extends ItemFactory<Ingredient, IngredientDTO> {

    @Override
    public Ingredient createItem() {
        return new Ingredient();
    }

    @Override
    public IngredientDTO createItemDTO() {
        return new IngredientDTO();
    }

}
