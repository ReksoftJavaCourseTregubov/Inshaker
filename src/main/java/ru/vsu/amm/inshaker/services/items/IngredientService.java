package ru.vsu.amm.inshaker.services.items;

import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.dto.converters.items.ItemMapper;
import ru.vsu.amm.inshaker.model.item.Ingredient;
import ru.vsu.amm.inshaker.repositories.ItemRepository;
import ru.vsu.amm.inshaker.repositories.ItemSubgroupRepository;
import ru.vsu.amm.inshaker.repositories.SearchRepository;
import ru.vsu.amm.inshaker.services.PropertiesService;
import ru.vsu.amm.inshaker.services.user.UserService;

import java.util.Collections;

@Service
public class IngredientService<T extends Ingredient> extends ItemService<T> {

    public IngredientService(UserService userService,
                             PropertiesService propertiesService,
                             SearchRepository searchRepository,
                             ItemSubgroupRepository itemSubgroupRepository,
                             ItemRepository<T> itemRepository,
                             ItemMapper<T> ingredientMapper) {
        super(userService, propertiesService, searchRepository, itemSubgroupRepository, itemRepository, ingredientMapper);
    }

    @Override
    public T add(T item) {
        item.setRecipePart(Collections.emptySet());
        return super.add(item);
    }

}
