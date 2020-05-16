package ru.vsu.amm.inshaker.dto.converters.items;

import lombok.Getter;
import org.dozer.Mapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.dto.simple.CocktailSimpleDTO;
import ru.vsu.amm.inshaker.dto.simple.ItemDTO;
import ru.vsu.amm.inshaker.exceptions.notfound.EntityNotFoundException;
import ru.vsu.amm.inshaker.model.cocktail.Cocktail;
import ru.vsu.amm.inshaker.model.item.Garnish;
import ru.vsu.amm.inshaker.model.item.Ingredient;
import ru.vsu.amm.inshaker.model.item.Item;
import ru.vsu.amm.inshaker.model.item.Tableware;
import ru.vsu.amm.inshaker.model.item.properties.ItemSubgroup;
import ru.vsu.amm.inshaker.repositories.CocktailRepository;
import ru.vsu.amm.inshaker.repositories.PropertiesRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ItemMapper<T extends Item> {

    private static final int PAGE_REQUEST_LIMIT = 5;

    @Getter
    private final PropertiesRepository propertiesRepository;
    private final CocktailRepository cocktailRepository;
    private final Mapper mapper;

    public ItemMapper(PropertiesRepository propertiesRepository, CocktailRepository cocktailRepository, Mapper mapper) {
        this.propertiesRepository = propertiesRepository;
        this.cocktailRepository = cocktailRepository;
        this.mapper = mapper;
    }

    public ItemDTO map(Item source) {
        ItemDTO result = mapper.map(source, ItemDTO.class);

        Set<Cocktail> cocktails = new HashSet<>();

        if (source instanceof Ingredient) {
            cocktails.addAll(cocktailRepository
                    .findAllByIngredient((Ingredient) source, PageRequest.of(0, PAGE_REQUEST_LIMIT)));
        }

        if (source instanceof Tableware) {
            cocktails.addAll(cocktailRepository
                    .findAllByGlass((Tableware) source, PageRequest.of(0, PAGE_REQUEST_LIMIT)));
            cocktails.addAll(cocktailRepository
                    .findAllByTool((Tableware) source, PageRequest.of(0, PAGE_REQUEST_LIMIT)));
        }

        if (source instanceof Garnish) {
            cocktails.addAll(cocktailRepository
                    .findAllByGarnish((Garnish) source, PageRequest.of(0, PAGE_REQUEST_LIMIT)));
        }

        result.setCocktails(cocktails
                .stream()
                .map(c -> mapper.map(c, CocktailSimpleDTO.class))
                .limit(PAGE_REQUEST_LIMIT)
                .collect(Collectors.toList())
        );

        return result;
    }

    public void map(T source, T destination) {
        destination.setItemSubgroup(Optional.ofNullable(source.getItemSubgroup())
                .map(t -> find(ItemSubgroup.class, t.getId()))
                .orElse(null));
    }

    protected <S> S find(Class<S> cls, Long id) {
        return propertiesRepository.findById(cls, id)
                .orElseThrow(() -> new EntityNotFoundException(cls, id));
    }

}
