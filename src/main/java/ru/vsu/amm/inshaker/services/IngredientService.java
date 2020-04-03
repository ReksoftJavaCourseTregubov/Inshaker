package ru.vsu.amm.inshaker.services;

import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.exceptions.IngredientNotFoundException;
import ru.vsu.amm.inshaker.model.Ingredient;
import ru.vsu.amm.inshaker.model.enums.Spirit;
import ru.vsu.amm.inshaker.repositories.IngredientRepository;

import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository repository;

    public IngredientService(IngredientRepository repository) {
        this.repository = repository;
    }

    public List<Ingredient> getAll() {
        return repository.findAll();
    }

    public List<Ingredient> getAll(String search, String spirit, String group, List<String> tastes) {
        Spirit s = Spirit.findByRuName(spirit);
        if (tastes == null) {
            return repository.findAllWithFilters(search, s.getRangeLow(), s.getRangeHigh(), group);
        } else {
            return repository.findAllWithFilters(search, s.getRangeLow(), s.getRangeHigh(), group, tastes, (long) tastes.size());
        }
    }

    public Ingredient get(Long id) {
        return repository.findById(id).orElseThrow(() -> new IngredientNotFoundException(id));
    }

    public List<String> getIngredientsGroups() {
        return repository.findDistinctIngredientGroups();
    }

    public List<String> getTastes() {
        return repository.findDistinctTastes();
    }

}
