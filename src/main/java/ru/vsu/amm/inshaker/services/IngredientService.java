package ru.vsu.amm.inshaker.services;

import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.exceptions.CocktailNotFoundException;
import ru.vsu.amm.inshaker.exceptions.IngredientNotFoundException;
import ru.vsu.amm.inshaker.model.Ingredient;
import ru.vsu.amm.inshaker.model.dto.IngredientDTO;
import ru.vsu.amm.inshaker.model.dto.IngredientSimpleDTO;
import ru.vsu.amm.inshaker.model.enums.Spirit;
import ru.vsu.amm.inshaker.repositories.IngredientRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IngredientService {

    private final IngredientRepository repository;
    private final Mapper mapper;

    public IngredientService(IngredientRepository repository, Mapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<Ingredient> getAllIngredients(String search, String spirit, String group, List<String> tastes) {
        if (search == null && spirit == null && group == null && tastes == null) {
            return repository.findAll();
        }

        Spirit s = Spirit.findByRuName(spirit);
        if (tastes == null) {
            return repository.findAllWithFilters(search, s.getRangeLow(), s.getRangeHigh(), group);
        } else {
            return repository.findAllWithFilters(search, s.getRangeLow(), s.getRangeHigh(), group, tastes, (long) tastes.size());
        }
    }

    public List<IngredientSimpleDTO> getAll(String search, String spirit, String group, List<String> tastes) {
        return getAllIngredients(search, spirit, group, tastes).stream()
                .map(i -> mapper.map(i, IngredientSimpleDTO.class)).collect(Collectors.toList());
    }

    public Ingredient getIngredient(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CocktailNotFoundException(id));
    }

    public IngredientDTO get(Long id) {
        return mapper.map(getIngredient(id), IngredientDTO.class);
    }

    public Set<String> getIngredientsGroups() {
        return repository.findDistinctIngredientGroups();
    }

    public Set<String> getTastes() {
        return repository.findDistinctTastes();
    }

    public Set<String> getSpirits() {
        return Arrays.stream(Spirit.values()).map(Spirit::getRuName).collect(Collectors.toSet());
    }

    public IngredientDTO add(IngredientDTO ingredient) {
        Ingredient newIngredient = mapper.map(ingredient, Ingredient.class);
        newIngredient.setId(null);
        return mapper.map(repository.save(newIngredient), IngredientDTO.class);
    }

    public IngredientDTO update(IngredientDTO newIngredient, Long id) {
        return repository.findById(id)
                .map(oldIngredient -> {
                    BeanUtils.copyProperties(mapper.map(newIngredient, Ingredient.class), oldIngredient);
                    oldIngredient.setId(id);
                    return mapper.map(repository.save(oldIngredient), IngredientDTO.class);
                }).orElseThrow(() -> new IngredientNotFoundException(id));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
