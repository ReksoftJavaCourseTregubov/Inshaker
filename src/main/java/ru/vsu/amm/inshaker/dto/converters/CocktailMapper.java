package ru.vsu.amm.inshaker.dto.converters;

import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.dto.converters.items.ItemMapper;
import ru.vsu.amm.inshaker.dto.entire.CocktailDTO;
import ru.vsu.amm.inshaker.dto.simple.CocktailSimpleDTO;
import ru.vsu.amm.inshaker.dto.simple.ItemDTO;
import ru.vsu.amm.inshaker.exceptions.notfound.EntityNotFoundException;
import ru.vsu.amm.inshaker.model.RecipePart;
import ru.vsu.amm.inshaker.model.Taste;
import ru.vsu.amm.inshaker.model.cocktail.Cocktail;
import ru.vsu.amm.inshaker.model.cocktail.CocktailGroup;
import ru.vsu.amm.inshaker.model.cocktail.CocktailSubgroup;
import ru.vsu.amm.inshaker.model.cocktail.MixingMethod;
import ru.vsu.amm.inshaker.model.item.Garnish;
import ru.vsu.amm.inshaker.model.item.Ingredient;
import ru.vsu.amm.inshaker.model.item.Item;
import ru.vsu.amm.inshaker.model.item.Tableware;
import ru.vsu.amm.inshaker.repositories.PropertiesRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CocktailMapper {

    private final PropertiesRepository propertiesRepository;
    private final ItemMapper<Item> itemMapper;
    private final Mapper mapper;

    public CocktailMapper(PropertiesRepository propertiesRepository,
                          ItemMapper<Item> itemMapper,
                          Mapper mapper) {
        this.propertiesRepository = propertiesRepository;
        this.itemMapper = itemMapper;
        this.mapper = mapper;
    }

    public CocktailDTO map(Cocktail source) {
        CocktailDTO result = mapper.map(source, CocktailDTO.class);
        result.setBase(source.getRecipePart()
                .stream()
                .filter(RecipePart::getIsBase)
                .findFirst()
                .map(i -> itemMapper.map(i.getIngredient()))
                .orElse(null));
        return result;
    }

    public CocktailSimpleDTO mapSimple(Cocktail source) {
        CocktailSimpleDTO result = mapper.map(source, CocktailSimpleDTO.class);
        result.setIngredients(source.getRecipePart()
                .stream()
                .map(i -> itemMapper.map(i.getIngredient()))
                .sorted(Comparator.comparing(ItemDTO::getId))
                .collect(Collectors.toList()));
        return result;
    }

    public void map(CocktailDTO source, Cocktail destination) {
        BeanUtils.copyProperties(source, destination,
                "glass", "garnish", "cocktailSubgroup", "setCocktailGroup", "mixingMethod", "taste", "recipePart");

        destination.setGlass(Optional.ofNullable(source.getGlass())
                .map(t -> find(Tableware.class, t.getId()))
                .orElse(null));
        destination.setGarnish(Optional.ofNullable(source.getGarnish())
                .map(t -> find(Garnish.class, t.getId()))
                .orElse(null));

        destination.setCocktailSubgroup(Optional.ofNullable(source.getCocktailSubgroup())
                .map(t -> find(CocktailSubgroup.class, t.getId()))
                .orElse(null));
        destination.setCocktailGroup(Optional.ofNullable(source.getCocktailGroup())
                .map(t -> find(CocktailGroup.class, t.getId()))
                .orElse(null));
        destination.setMixingMethod(Optional.ofNullable(source.getMixingMethod())
                .map(t -> find(MixingMethod.class, t.getId()))
                .orElse(null));

        if (destination.getTaste() == null) {
            destination.setTaste(new HashSet<>());
        } else {
            destination.getTaste().clear();
        }
        destination.getTaste().addAll(tastes(source));

        if (destination.getRecipePart() == null) {
            destination.setRecipePart(new HashSet<>());
        } else {
            destination.getRecipePart().clear();
        }
        destination.getRecipePart().addAll((recipePart(source, destination)));
    }

    private Set<RecipePart> recipePart(CocktailDTO source, Cocktail destination) {
        return Optional.ofNullable(source.getRecipePart())
                .map(t -> t.stream()
                        .map(dto -> {
                            RecipePart r = propertiesRepository
                                    .findRecipePartById(destination.getId(), dto.getIngredient().getId())
                                    .orElseGet(() -> {
                                        RecipePart s = new RecipePart();
                                        s.setCocktail(destination);
                                        s.setIngredient(find(Ingredient.class, dto.getIngredient().getId()));
                                        return s;
                                    });
                            r.setAmount(dto.getAmount());
                            r.setIsBase(r.getIngredient().getId().equals(source.getBase().getId()));
                            return r;
                        }).collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
    }

    private Set<Taste> tastes(CocktailDTO source) {
        return Optional.ofNullable(source.getTaste())
                .map(s -> s.stream()
                        .map(t -> find(Taste.class, t.getId()))
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
    }

    private <T> T find(Class<T> cls, Long id) {
        return propertiesRepository.findById(cls, id)
                .orElseThrow(() -> new EntityNotFoundException(cls, id));
    }

}
