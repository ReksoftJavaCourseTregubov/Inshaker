package ru.vsu.amm.inshaker.dto.converters.items;

import lombok.Getter;
import org.dozer.Mapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.dto.entire.items.ItemDTO;
import ru.vsu.amm.inshaker.dto.simple.CocktailSimpleDTO;
import ru.vsu.amm.inshaker.exceptions.NotBlankException;
import ru.vsu.amm.inshaker.exceptions.notfound.EntityNotFoundException;
import ru.vsu.amm.inshaker.model.RecipePart;
import ru.vsu.amm.inshaker.model.cocktail.Cocktail;
import ru.vsu.amm.inshaker.model.item.Garnish;
import ru.vsu.amm.inshaker.model.item.Ingredient;
import ru.vsu.amm.inshaker.model.item.Item;
import ru.vsu.amm.inshaker.model.item.Tableware;
import ru.vsu.amm.inshaker.model.item.properties.ItemGroup;
import ru.vsu.amm.inshaker.model.item.properties.ItemSubgroup;
import ru.vsu.amm.inshaker.repositories.CocktailRepository;
import ru.vsu.amm.inshaker.repositories.ItemSubgroupRepository;
import ru.vsu.amm.inshaker.repositories.PropertiesRepository;
import ru.vsu.amm.inshaker.services.factory.ItemFactory;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ItemMapper<T extends Item, S extends ItemDTO> {

    private static final int PAGE_REQUEST_LIMIT = 5;

    @Getter
    private final PropertiesRepository propertiesRepository;
    private final CocktailRepository cocktailRepository;
    private final ItemSubgroupRepository itemSubgroupRepository;
    private final Mapper mapper;
    private final ItemFactory<T, S> itemFactory;

    public ItemMapper(PropertiesRepository propertiesRepository,
                      CocktailRepository cocktailRepository,
                      ItemSubgroupRepository itemSubgroupRepository,
                      Mapper mapper,
                      ItemFactory<T, S> itemFactory) {
        this.propertiesRepository = propertiesRepository;
        this.cocktailRepository = cocktailRepository;
        this.itemSubgroupRepository = itemSubgroupRepository;
        this.mapper = mapper;
        this.itemFactory = itemFactory;
    }

    public ItemDTO mapSimple(Item source) {
        ItemDTO result = mapper.map(source, ItemDTO.class);
        result.setCocktails(cocktails(source, PAGE_REQUEST_LIMIT));
        return result;
    }

    public S map(T source) {
        S result = itemFactory.createItemDTO();
        mapper.map(source, result);
        result.setCocktails(cocktails(source, Integer.MAX_VALUE));
        return result;
    }

    public void map(S source, T destination) {
        destination.setItemSubgroup(Optional.ofNullable(source.getItemSubgroup())
                .map(t -> propertiesRepository.findById(ItemSubgroup.class, t.getId())
                        .orElseGet(() -> {
                            ItemSubgroup s = new ItemSubgroup();
                            s.setId(null);
                            s.setName(Optional.ofNullable(t.getName())
                                    .orElseThrow(() -> new NotBlankException("ItemGroup name")));
                            s.setItemGroup(Optional.ofNullable(t.getItemGroup())
                                    .map(r -> find(ItemGroup.class, r.getId()))
                                    .orElseThrow(() -> new EntityNotFoundException(ItemGroup.class, null)));
                            return itemSubgroupRepository.save(s);
                        }))
                .orElseThrow(() -> new NotBlankException("ItemSubgroup")));
    }

    protected <F> F find(Class<F> cls, Long id) {
        return propertiesRepository.findById(cls, id)
                .orElseThrow(() -> new EntityNotFoundException(cls, id));
    }

    private List<CocktailSimpleDTO> cocktails(Item source, int limit) {
        Set<Cocktail> cocktails = new HashSet<>();

        if (source instanceof Ingredient) {
            cocktails.addAll(Optional.ofNullable(((Ingredient) source).getRecipePart())
                    .map(t -> t.stream()
                            .map(RecipePart::getCocktail)
                            .limit(limit)
                            .collect(Collectors.toSet()))
                    .orElse(Collections.emptySet()));
        }

        if (source instanceof Tableware) {
            cocktails.addAll(cocktailRepository
                    .findAllByGlass((Tableware) source, PageRequest.of(0, limit)));
            cocktails.addAll(cocktailRepository
                    .findAllByTool((Tableware) source, PageRequest.of(0, limit)));
        }

        if (source instanceof Garnish) {
            cocktails.addAll(cocktailRepository
                    .findAllByGarnish((Garnish) source, PageRequest.of(0, limit)));
        }

        return cocktails.stream()
                .map(c -> mapper.map(c, CocktailSimpleDTO.class))
                .limit(limit)
                .collect(Collectors.toList());
    }

}
