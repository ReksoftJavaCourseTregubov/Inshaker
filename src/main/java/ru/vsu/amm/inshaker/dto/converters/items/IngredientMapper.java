package ru.vsu.amm.inshaker.dto.converters.items;

import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.dto.entire.items.IngredientDTO;
import ru.vsu.amm.inshaker.model.Taste;
import ru.vsu.amm.inshaker.model.item.Ingredient;
import ru.vsu.amm.inshaker.model.item.properties.Country;
import ru.vsu.amm.inshaker.model.item.properties.IngredientBase;
import ru.vsu.amm.inshaker.repositories.CocktailRepository;
import ru.vsu.amm.inshaker.repositories.ItemSubgroupRepository;
import ru.vsu.amm.inshaker.repositories.PropertiesRepository;
import ru.vsu.amm.inshaker.services.factory.ItemFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IngredientMapper<T extends Ingredient, S extends IngredientDTO> extends ItemMapper<T, S> {

    public IngredientMapper(PropertiesRepository propertiesRepository,
                            CocktailRepository cocktailRepository,
                            ItemSubgroupRepository itemSubgroupRepository,
                            Mapper mapper,
                            ItemFactory<T, S> ingredientFactory) {
        super(propertiesRepository, cocktailRepository, itemSubgroupRepository, mapper, ingredientFactory);
    }

    @Override
    public void map(S source, T destination) {
        super.map(source, destination);
        BeanUtils.copyProperties(source, destination,
                "itemSubgroup", "ingredientBase", "country", "taste", "recipePart");

        destination.setIngredientBase(Optional.ofNullable(source.getIngredientBase())
                .map(t -> find(IngredientBase.class, t.getId()))
                .orElse(null));
        destination.setCountry(Optional.ofNullable(source.getCountry())
                .map(t -> find(Country.class, t.getId()))
                .orElse(null));

        Set<Taste> tastes = tastes(source);
        if (destination.getTaste() == null) {
            destination.setTaste(new HashSet<>());
        } else {
            destination.getTaste().clear();
        }
        destination.getTaste().addAll(tastes);
    }

    private Set<Taste> tastes(IngredientDTO source) {
        return Optional.ofNullable(source.getTaste())
                .map(s -> s.stream()
                        .map(t -> find(Taste.class, t.getId()))
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
    }

}
