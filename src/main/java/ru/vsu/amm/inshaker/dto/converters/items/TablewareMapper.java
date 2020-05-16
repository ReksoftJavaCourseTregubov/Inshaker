package ru.vsu.amm.inshaker.dto.converters.items;

import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.model.item.Tableware;
import ru.vsu.amm.inshaker.repositories.CocktailRepository;
import ru.vsu.amm.inshaker.repositories.PropertiesRepository;

@Service
public class TablewareMapper extends ItemMapper<Tableware> {

    public TablewareMapper(PropertiesRepository propertiesRepository, CocktailRepository cocktailRepository, Mapper mapper) {
        super(propertiesRepository, cocktailRepository, mapper);
    }

    @Override
    public void map(Tableware source, Tableware destination) {
        super.map(source, destination);
        BeanUtils.copyProperties(source, destination, "itemSubgroup");
    }

}
