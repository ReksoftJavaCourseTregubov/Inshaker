package ru.vsu.amm.inshaker.model.dto.converters;

import org.dozer.Mapper;
import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.model.Cocktail;
import ru.vsu.amm.inshaker.model.Recipe;
import ru.vsu.amm.inshaker.model.dto.entire.CocktailDTO;
import ru.vsu.amm.inshaker.model.dto.entire.RecipeDTO;
import ru.vsu.amm.inshaker.model.dto.simple.CocktailSimpleDTO;
import ru.vsu.amm.inshaker.services.IngredientService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CocktailDTOConverter {

    private final IngredientService service;
    private final Mapper mapper;

    public CocktailDTOConverter(IngredientService service, Mapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    public Cocktail convert(CocktailDTO source) {
        Cocktail destination = mapper.map(source, Cocktail.class);
        try {
            destination.setBase(service.getIngredient(source.getBase().getId()));
            destination.setGlass(service.getIngredient(source.getGlass().getId()));
            destination.setGarnish(service.getIngredient(source.getGarnish().getId()));
        } catch (NullPointerException ignored) {
        }
        destination.setRecipe(convert(source.getRecipe(), destination));
        return destination;
    }

    public CocktailDTO convert(Cocktail source) {
        return mapper.map(source, CocktailDTO.class);
    }

    public CocktailSimpleDTO convertSimple(Cocktail source) {
        return mapper.map(source, CocktailSimpleDTO.class);
    }

    private Set<Recipe> convert(Set<RecipeDTO> recipe, Cocktail cocktail) {
        return recipe.stream()
                .map(dto -> {
                    Recipe r = new Recipe();
                    r.setCocktail(cocktail);
                    r.setIngredient(service.getIngredient(dto.getIngredient().getId()));
                    r.setAmount(dto.getAmount());
                    return r;
                }).collect(Collectors.toSet());
    }

}
