package ru.vsu.amm.inshaker.dto.converters.items;

import org.dozer.Mapper;
import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.model.item.Garnish;
import ru.vsu.amm.inshaker.repositories.CocktailRepository;
import ru.vsu.amm.inshaker.repositories.PropertiesRepository;

@Service
public class GarnishMapper extends IngredientMapper<Garnish> {

    public GarnishMapper(PropertiesRepository propertiesRepository, CocktailRepository cocktailRepository, Mapper mapper) {
        super(propertiesRepository, cocktailRepository, mapper);
    }

}
