package ru.vsu.amm.inshaker.dto.converters;

import org.dozer.Mapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.dto.simple.ItemDTO;
import ru.vsu.amm.inshaker.model.cocktail.Cocktail;
import ru.vsu.amm.inshaker.dto.simple.CocktailSimpleDTO;
import ru.vsu.amm.inshaker.model.item.Garnish;
import ru.vsu.amm.inshaker.model.item.Ingredient;
import ru.vsu.amm.inshaker.model.item.Item;
import ru.vsu.amm.inshaker.model.item.Tableware;
import ru.vsu.amm.inshaker.repositories.CocktailRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ItemMapper {

    private static final int PAGE_REQUEST_LIMIT = 5;

    private final CocktailRepository repository;
    private final Mapper mapper;

    public ItemMapper(CocktailRepository repository, Mapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ItemDTO map(Item source) {
        ItemDTO result = mapper.map(source, ItemDTO.class);

        Set<Cocktail> cocktails = new HashSet<>();

        if (source instanceof Ingredient) {
            cocktails.addAll(repository
                    .findAllByIngredient((Ingredient) source, PageRequest.of(0, PAGE_REQUEST_LIMIT)));
        }

        if (source instanceof Tableware) {
            cocktails.addAll(repository
                    .findAllByGlass((Tableware) source, PageRequest.of(0, PAGE_REQUEST_LIMIT)));
            cocktails.addAll(repository
                    .findAllByTool((Tableware) source, PageRequest.of(0, PAGE_REQUEST_LIMIT)));
        }

        if (source instanceof Garnish) {
            cocktails.addAll(repository
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

}
