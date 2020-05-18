package ru.vsu.amm.inshaker.services;

import org.dozer.Mapper;
import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.dto.entire.MixingMethodDTO;
import ru.vsu.amm.inshaker.dto.properties.CocktailPropertiesDTO;
import ru.vsu.amm.inshaker.dto.properties.IngredientPropertiesDTO;
import ru.vsu.amm.inshaker.exceptions.notfound.EntityNotFoundException;
import ru.vsu.amm.inshaker.model.cocktail.CocktailGroup;
import ru.vsu.amm.inshaker.model.cocktail.CocktailSubgroup;
import ru.vsu.amm.inshaker.model.cocktail.MixingMethod;
import ru.vsu.amm.inshaker.model.enums.Spirit;
import ru.vsu.amm.inshaker.model.item.properties.ItemGroup;
import ru.vsu.amm.inshaker.repositories.PropertiesRepository;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class PropertiesService {

    private final PropertiesRepository repository;
    private final Mapper mapper;

    public PropertiesService(PropertiesRepository repository, Mapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public CocktailPropertiesDTO getCocktailProperties() {
        CocktailPropertiesDTO properties = new CocktailPropertiesDTO();

        properties.setBases(repository.findDistinctCocktailBases());
        properties.setGroups(repository.findAllDistinct(CocktailGroup.class));
        properties.setSubgroups(repository.findAllDistinct(CocktailSubgroup.class));
        properties.setTastes(repository.findDistinctCocktailTastes());
        properties.setMixingMethods(repository.findAllDistinct(MixingMethod.class)
                .stream()
                .map(t -> mapper.map(t, MixingMethodDTO.class))
                .collect(Collectors.toList()));
        properties.setSpirits(Arrays.asList(Spirit.values()));

        return properties;
    }

    public IngredientPropertiesDTO getIngredientProperties() {
        IngredientPropertiesDTO properties = new IngredientPropertiesDTO();

        properties.setGroups(repository.findAllDistinct(ItemGroup.class));
        properties.setTastes(repository.findDistinctIngredientTastes());
        properties.setSpirits(Arrays.asList(Spirit.values()));

        return properties;
    }

    public MixingMethod getMixingMethod(Long id) {
        return repository.findById(MixingMethod.class, id)
                .orElseThrow(() -> new EntityNotFoundException(MixingMethod.class, id));
    }

}
