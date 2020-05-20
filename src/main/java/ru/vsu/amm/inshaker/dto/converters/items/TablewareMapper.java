package ru.vsu.amm.inshaker.dto.converters.items;

import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.dto.entire.items.TablewareDTO;
import ru.vsu.amm.inshaker.model.item.Tableware;
import ru.vsu.amm.inshaker.repositories.CocktailRepository;
import ru.vsu.amm.inshaker.repositories.ItemSubgroupRepository;
import ru.vsu.amm.inshaker.repositories.PropertiesRepository;
import ru.vsu.amm.inshaker.services.factory.TablewareFactory;

@Service
public class TablewareMapper extends ItemMapper<Tableware, TablewareDTO> {

    public TablewareMapper(PropertiesRepository propertiesRepository,
                           CocktailRepository cocktailRepository,
                           ItemSubgroupRepository itemSubgroupRepository,
                           Mapper mapper,
                           TablewareFactory tablewareFactory) {
        super(propertiesRepository, cocktailRepository, itemSubgroupRepository, mapper, tablewareFactory);
    }

    @Override
    public void map(TablewareDTO source, Tableware destination) {
        super.map(source, destination);
        BeanUtils.copyProperties(source, destination, "itemSubgroup");
    }

}
