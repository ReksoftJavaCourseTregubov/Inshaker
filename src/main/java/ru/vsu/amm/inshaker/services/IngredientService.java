package ru.vsu.amm.inshaker.services;

import org.dozer.Mapper;
import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.exceptions.CocktailNotFoundException;
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

    public IngredientDTO get(Long id) {
        return mapper.map(repository.findById(id)
                .orElseThrow(() -> new CocktailNotFoundException(id)), IngredientDTO.class);
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

}
