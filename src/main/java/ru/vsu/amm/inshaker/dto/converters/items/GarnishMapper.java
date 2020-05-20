package ru.vsu.amm.inshaker.dto.converters.items;

import org.dozer.Mapper;
import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.dto.entire.items.GarnishDTO;
import ru.vsu.amm.inshaker.model.item.Garnish;
import ru.vsu.amm.inshaker.repositories.CocktailRepository;
import ru.vsu.amm.inshaker.repositories.ItemSubgroupRepository;
import ru.vsu.amm.inshaker.repositories.PropertiesRepository;
import ru.vsu.amm.inshaker.services.factory.GarnishFactory;

@Service
public class GarnishMapper extends IngredientMapper<Garnish, GarnishDTO> {

    public GarnishMapper(PropertiesRepository propertiesRepository,
                         CocktailRepository cocktailRepository,
                         ItemSubgroupRepository itemSubgroupRepository,
                         Mapper mapper,
                         GarnishFactory garnishFactory) {
        super(propertiesRepository, cocktailRepository, itemSubgroupRepository, mapper, garnishFactory);
    }

}
