package ru.vsu.amm.inshaker.services;

import org.dozer.Mapper;
import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.exceptions.CocktailNotFoundException;
import ru.vsu.amm.inshaker.model.dto.CocktailDTO;
import ru.vsu.amm.inshaker.model.dto.CocktailSimpleDTO;
import ru.vsu.amm.inshaker.model.enums.Spirit;
import ru.vsu.amm.inshaker.repositories.CocktailRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CocktailService {

    private final CocktailRepository repository;
    private final Mapper mapper;

    public CocktailService(CocktailRepository repository, Mapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<CocktailSimpleDTO> getAll(String search, String base, String spirit, String group, List<String> tastes) {
        if (search == null && base == null && spirit == null && group == null && tastes == null) {
            return repository.findAll().stream()
                    .map(c -> mapper.map(c, CocktailSimpleDTO.class)).collect(Collectors.toList());
        }

        Spirit s = Spirit.findByRuName(spirit);
        if (tastes == null) {
            return repository.findAllWithFilters(search, base, s.getRangeLow(), s.getRangeHigh(), group).stream()
                    .map(c -> mapper.map(c, CocktailSimpleDTO.class)).collect(Collectors.toList());
        } else {
            return repository.findAllWithFilters(search, base, s.getRangeLow(), s.getRangeHigh(), group, tastes, (long) tastes.size()).stream()
                    .map(c -> mapper.map(c, CocktailSimpleDTO.class)).collect(Collectors.toList());
        }
    }

    public CocktailDTO get(Long id) {
        return mapper.map(repository.findById(id)
                .orElseThrow(() -> new CocktailNotFoundException(id)), CocktailDTO.class);
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
