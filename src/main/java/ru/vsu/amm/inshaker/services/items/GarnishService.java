package ru.vsu.amm.inshaker.services.items;

import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.dto.converters.items.ItemMapper;
import ru.vsu.amm.inshaker.model.item.Garnish;
import ru.vsu.amm.inshaker.repositories.ItemRepository;
import ru.vsu.amm.inshaker.repositories.SearchRepository;
import ru.vsu.amm.inshaker.services.PropertiesService;
import ru.vsu.amm.inshaker.services.user.UserService;

@Service
public class GarnishService extends IngredientService<Garnish> {

    public GarnishService(UserService userService,
                          PropertiesService propertiesService,
                          SearchRepository searchRepository,
                          ItemRepository<Garnish> itemRepository,
                          ItemMapper<Garnish> garnishMapper) {
        super(userService, propertiesService, searchRepository, itemRepository, garnishMapper);
    }

}
