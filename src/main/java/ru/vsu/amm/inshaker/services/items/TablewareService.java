package ru.vsu.amm.inshaker.services.items;

import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.dto.converters.items.ItemMapper;
import ru.vsu.amm.inshaker.model.item.Tableware;
import ru.vsu.amm.inshaker.repositories.ItemRepository;
import ru.vsu.amm.inshaker.repositories.ItemSubgroupRepository;
import ru.vsu.amm.inshaker.repositories.SearchRepository;
import ru.vsu.amm.inshaker.services.PropertiesService;
import ru.vsu.amm.inshaker.services.user.UserService;

@Service
public class TablewareService extends ItemService<Tableware> {

    public TablewareService(UserService userService,
                            PropertiesService propertiesService,
                            SearchRepository searchRepository,
                            ItemSubgroupRepository itemSubgroupRepository,
                            ItemRepository<Tableware> itemRepository,
                            ItemMapper<Tableware> tablewareMapper) {
        super(userService, propertiesService, searchRepository, itemSubgroupRepository, itemRepository, tablewareMapper);
    }

}
