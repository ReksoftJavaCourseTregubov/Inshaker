package ru.vsu.amm.inshaker.services;

import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.exceptions.CocktailNotFoundException;
import ru.vsu.amm.inshaker.model.Cocktail;
import ru.vsu.amm.inshaker.model.enums.Spirit;
import ru.vsu.amm.inshaker.repositories.CocktailRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CocktailService {

    private final CocktailRepository repository;

    public CocktailService(CocktailRepository repository) {
        this.repository = repository;
    }

    public List<Cocktail> getAll(String search, String base, String spirit, String group, List<String> tastes) {
        if (search == null && base == null && spirit == null && group == null && tastes == null) {
            return repository.findAll();
        }

        Spirit s = Spirit.findByRuName(spirit);
        if (tastes == null) {
            return repository.findAllWithFilters(search, base, s.getRangeLow(), s.getRangeHigh(), group);
        } else {
            return repository.findAllWithFilters(search, base, s.getRangeLow(), s.getRangeHigh(), group, tastes, (long) tastes.size());
        }
    }

    public Cocktail get(Long id) {
        return repository.findById(id).orElseThrow(() -> new CocktailNotFoundException(id));
    }

    public Set<String> getBases() {
        return repository.findDistinctBases();
    }

    public Set<String> getCocktailsGroups() {
        return repository.findDistinctCocktailGroups();
    }

    public Set<String> getTastes() {
        return repository.findDistinctTastes();
    }

    public Set<String> getSpirits() {
        return Arrays.stream(Spirit.values()).map(Spirit::getRuName).collect(Collectors.toSet());
    }

}
