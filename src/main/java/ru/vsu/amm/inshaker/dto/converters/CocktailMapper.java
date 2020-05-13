package ru.vsu.amm.inshaker.dto.converters;

import org.dozer.Mapper;
import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.dto.entire.CocktailDTO;
import ru.vsu.amm.inshaker.dto.entire.RecipePartDTO;
import ru.vsu.amm.inshaker.dto.simple.CocktailSimpleDTO;
import ru.vsu.amm.inshaker.dto.simple.ItemDTO;
import ru.vsu.amm.inshaker.exceptions.notfound.CocktailNotFoundException;
import ru.vsu.amm.inshaker.model.RecipePart;
import ru.vsu.amm.inshaker.model.Taste;
import ru.vsu.amm.inshaker.model.cocktail.Cocktail;
import ru.vsu.amm.inshaker.model.item.Ingredient;
import ru.vsu.amm.inshaker.repositories.PropertiesRepository;
import ru.vsu.amm.inshaker.services.ItemService;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CocktailMapper {

    private final PropertiesRepository propertiesRepository;
    private final ItemService<Ingredient> service;
    private final ItemMapper itemMapper;
    private final Mapper mapper;

    public CocktailMapper(PropertiesRepository propertiesRepository,
                          ItemService<Ingredient> service,
                          ItemMapper itemMapper,
                          Mapper mapper) {
        this.propertiesRepository = propertiesRepository;
        this.service = service;
        this.itemMapper = itemMapper;
        this.mapper = mapper;
    }

    public Cocktail map(CocktailDTO source) {
        Cocktail destination = mapper.map(source, Cocktail.class);
        destination.setTaste(tastes(source));
        destination.setRecipePart(recipePart(source.getRecipePart(), destination, source.getBase().getId()));
        return destination;
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

    private Set<RecipePart> recipePart(Set<RecipePartDTO> recipe, Cocktail cocktail, Long baseId) {
        return recipe.stream()
                .map(dto -> {
                    RecipePart r = new RecipePart();
                    r.setCocktail(cocktail);
                    r.setIngredient(service.getItem(dto.getIngredient().getId()));
                    r.setAmount(dto.getAmount());
                    r.setIsBase(r.getIngredient().getId().equals(baseId));
                    return r;
                }).collect(Collectors.toSet());
    }

    private Set<Taste> tastes(CocktailDTO cocktail) {
        return cocktail.getTaste()
                .stream()
                .map(t -> propertiesRepository.findById(Taste.class, t.getId())
                        .orElseThrow(() -> new CocktailNotFoundException(t.getId())))
                .collect(Collectors.toSet());
    }

}
