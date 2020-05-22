package ru.vsu.amm.inshaker.services.items;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.amm.inshaker.dto.converters.items.TablewareMapper;
import ru.vsu.amm.inshaker.dto.entire.items.TablewareDTO;
import ru.vsu.amm.inshaker.model.item.Tableware;
import ru.vsu.amm.inshaker.repositories.ItemRepository;
import ru.vsu.amm.inshaker.repositories.ItemSubgroupRepository;
import ru.vsu.amm.inshaker.repositories.SearchRepository;
import ru.vsu.amm.inshaker.services.PropertiesService;
import ru.vsu.amm.inshaker.services.factory.ItemFactory;
import ru.vsu.amm.inshaker.services.user.UserService;

@Service
public class TablewareService extends ItemService<Tableware, TablewareDTO> {

    public TablewareService(UserService userService,
                            PropertiesService propertiesService,
                            SearchRepository searchRepository,
                            ItemSubgroupRepository itemSubgroupRepository,
                            ItemRepository<Tableware> itemRepository,
                            TablewareMapper tablewareMapper,
                            ItemFactory<Tableware, TablewareDTO> tablewareFactory) {
        super(userService, propertiesService, searchRepository, itemSubgroupRepository, itemRepository, tablewareMapper, tablewareFactory);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        getItemRepository().deleteMixingMethodTablewareByItemId(id);
        getItemRepository().nullifyCocktailGlassByItemId(id);
        super.delete(id);
    }

}
