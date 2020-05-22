package ru.vsu.amm.inshaker.services.items;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.amm.inshaker.dto.converters.items.IngredientMapper;
import ru.vsu.amm.inshaker.dto.entire.items.IngredientDTO;
import ru.vsu.amm.inshaker.model.item.Ingredient;
import ru.vsu.amm.inshaker.repositories.ItemRepository;
import ru.vsu.amm.inshaker.repositories.ItemSubgroupRepository;
import ru.vsu.amm.inshaker.repositories.SearchRepository;
import ru.vsu.amm.inshaker.services.PropertiesService;
import ru.vsu.amm.inshaker.services.factory.ItemFactory;
import ru.vsu.amm.inshaker.services.user.UserService;

import java.util.Collections;

@Service
public class IngredientService<T extends Ingredient, S extends IngredientDTO> extends ItemService<T, S> {

    public IngredientService(UserService userService,
                             PropertiesService propertiesService,
                             SearchRepository searchRepository,
                             ItemSubgroupRepository itemSubgroupRepository,
                             ItemRepository<T> itemRepository,
                             IngredientMapper<T, S> ingredientMapper,
                             ItemFactory<T, S> ingredientFactory) {
        super(userService, propertiesService, searchRepository, itemSubgroupRepository, itemRepository, ingredientMapper, ingredientFactory);
    }

    @Override
    public S add(S item) {
        item.setCocktails(Collections.emptyList());
        return super.add(item);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        getItemRepository().deleteUserBarByItemId(id);
        super.delete(id);
    }

}
