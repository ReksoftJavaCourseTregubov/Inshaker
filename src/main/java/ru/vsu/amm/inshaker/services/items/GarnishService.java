package ru.vsu.amm.inshaker.services.items;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.amm.inshaker.dto.converters.items.GarnishMapper;
import ru.vsu.amm.inshaker.dto.entire.items.GarnishDTO;
import ru.vsu.amm.inshaker.model.item.Garnish;
import ru.vsu.amm.inshaker.repositories.ItemRepository;
import ru.vsu.amm.inshaker.repositories.ItemSubgroupRepository;
import ru.vsu.amm.inshaker.repositories.SearchRepository;
import ru.vsu.amm.inshaker.services.PropertiesService;
import ru.vsu.amm.inshaker.services.factory.ItemFactory;
import ru.vsu.amm.inshaker.services.user.UserService;

@Service
public class GarnishService extends IngredientService<Garnish, GarnishDTO> {

    public GarnishService(UserService userService,
                          PropertiesService propertiesService,
                          SearchRepository searchRepository,
                          ItemSubgroupRepository itemSubgroupRepository,
                          ItemRepository<Garnish> itemRepository,
                          GarnishMapper garnishMapper,
                          ItemFactory<Garnish, GarnishDTO> garnishFactory) {
        super(userService, propertiesService, searchRepository, itemSubgroupRepository, itemRepository, garnishMapper, garnishFactory);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        getItemRepository().nullifyCocktailGarnishByItemId(id);
        super.delete(id);
    }

}
