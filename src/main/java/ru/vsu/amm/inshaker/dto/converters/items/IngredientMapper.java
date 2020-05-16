package ru.vsu.amm.inshaker.dto.converters.items;

import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.model.RecipePart;
import ru.vsu.amm.inshaker.model.Taste;
import ru.vsu.amm.inshaker.model.cocktail.Cocktail;
import ru.vsu.amm.inshaker.model.item.Ingredient;
import ru.vsu.amm.inshaker.model.item.properties.Country;
import ru.vsu.amm.inshaker.model.item.properties.IngredientBase;
import ru.vsu.amm.inshaker.repositories.CocktailRepository;
import ru.vsu.amm.inshaker.repositories.PropertiesRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IngredientMapper<T extends Ingredient> extends ItemMapper<T> {

    public IngredientMapper(PropertiesRepository propertiesRepository, CocktailRepository cocktailRepository, Mapper mapper) {
        super(propertiesRepository, cocktailRepository, mapper);
    }

    @Override
    public void map(T source, T destination) {
        super.map(source, destination);
        BeanUtils.copyProperties(source, destination,
                "itemSubgroup", "ingredientBase", "country", "taste", "recipePart");

        destination.setIngredientBase(Optional.ofNullable(source.getIngredientBase())
                .map(t -> find(IngredientBase.class, t.getId()))
                .orElse(null));
        destination.setCountry(Optional.ofNullable(source.getCountry())
                .map(t -> find(Country.class, t.getId()))
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

    private Set<RecipePart> recipePart(Ingredient source, Ingredient destination) {
        return Optional.ofNullable(source.getRecipePart())
                .map(t -> t.stream()
                        .map(dto -> {
                            RecipePart r = getPropertiesRepository()
                                    .findRecipePartById(dto.getCocktail().getId(), destination.getId())
                                    .orElseGet(() -> {
                                        RecipePart s = new RecipePart();
                                        s.setIngredient(destination);
                                        s.setCocktail(find(Cocktail.class, dto.getCocktail().getId()));
                                        return s;
                                    });
                            r.setAmount(dto.getAmount());
                            r.setIsBase(dto.getIsBase());
                            return r;
                        }).collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
    }

    private Set<Taste> tastes(Ingredient source) {
        return Optional.ofNullable(source.getTaste())
                .map(s -> s.stream()
                        .map(t -> find(Taste.class, t.getId()))
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
    }

}
