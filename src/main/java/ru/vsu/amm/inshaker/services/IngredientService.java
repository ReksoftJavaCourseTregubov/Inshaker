package ru.vsu.amm.inshaker.services;

import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.exceptions.AnonymousAuthenticationException;
import ru.vsu.amm.inshaker.exceptions.notfound.CocktailNotFoundException;
import ru.vsu.amm.inshaker.exceptions.notfound.IngredientNotFoundException;
import ru.vsu.amm.inshaker.model.Ingredient;
import ru.vsu.amm.inshaker.model.dto.entire.IngredientDTO;
import ru.vsu.amm.inshaker.model.dto.simple.IngredientSimpleDTO;
import ru.vsu.amm.inshaker.model.enums.Spirit;
import ru.vsu.amm.inshaker.repositories.IngredientRepository;
import ru.vsu.amm.inshaker.services.user.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final UserService userService;
    private final Mapper mapper;

    public IngredientService(IngredientRepository ingredientRepository,
                             UserService userService,
                             Mapper mapper) {
        this.ingredientRepository = ingredientRepository;
        this.userService = userService;
        this.mapper = mapper;
    }


    public Ingredient getIngredient(Long id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new CocktailNotFoundException(id));
    }

    public List<Ingredient> getAllIngredients(String search, String spirit, String group, List<String> tastes) {
        if (search == null && spirit == null && group == null && tastes == null) {
            return ingredientRepository.findAll();
        }

        Spirit s = Spirit.findByRuName(spirit);
        if (tastes == null) {
            return ingredientRepository.findAllWithFilters(search, s.getRangeLow(), s.getRangeHigh(), group);
        } else {
            return ingredientRepository.findAllWithFilters(search, s.getRangeLow(), s.getRangeHigh(), group, tastes, (long) tastes.size());
        }
    }


    public IngredientDTO get(Long id) {
        Ingredient ingredient = getIngredient(id);
        IngredientDTO ingredientDTO = mapper.map(ingredient, IngredientDTO.class);

        try {
            if (userService.getCurrentUser().getBar().contains(ingredient)) {
                ingredientDTO.setInBar(true);
            }
        } catch (AnonymousAuthenticationException ignored) {
        }

        return ingredientDTO;
    }

    public List<IngredientSimpleDTO> getAll(String search, String spirit, String group, List<String> tastes) {
        return getAllIngredients(search, spirit, group, tastes).stream()
                .map(i -> mapper.map(i, IngredientSimpleDTO.class)).collect(Collectors.toList());
    }


    public IngredientDTO add(IngredientDTO ingredient) {
        Ingredient newIngredient = mapper.map(ingredient, Ingredient.class);
        newIngredient.setId(null);
        return mapper.map(ingredientRepository.save(newIngredient), IngredientDTO.class);
    }

    public IngredientDTO update(IngredientDTO newIngredient, Long id) {
        return ingredientRepository.findById(id)
                .map(oldIngredient -> {
                    BeanUtils.copyProperties(mapper.map(newIngredient, Ingredient.class), oldIngredient);
                    oldIngredient.setId(id);
                    return mapper.map(ingredientRepository.save(oldIngredient), IngredientDTO.class);
                }).orElseThrow(() -> new IngredientNotFoundException(id));
    }

    public void delete(Long id) {
        if (ingredientRepository.existsById(id)) {
            ingredientRepository.deleteById(id);
        } else throw new IngredientNotFoundException(id);
    }


    public Set<String> getIngredientsGroups() {
        return ingredientRepository.findDistinctIngredientGroups();
    }

    public Set<String> getTastes() {
        return ingredientRepository.findDistinctTastes();
    }

    public Set<String> getSpirits() {
        return Arrays.stream(Spirit.values()).map(Spirit::getRuName).collect(Collectors.toSet());
    }

}
