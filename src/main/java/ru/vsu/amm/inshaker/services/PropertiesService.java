package ru.vsu.amm.inshaker.services;

import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.dto.properties.CocktailPropertiesDTO;
import ru.vsu.amm.inshaker.dto.properties.IngredientPropertiesDTO;
import ru.vsu.amm.inshaker.model.cocktail.CocktailGroup;
import ru.vsu.amm.inshaker.model.cocktail.CocktailSubgroup;
import ru.vsu.amm.inshaker.model.cocktail.MixingMethod;
import ru.vsu.amm.inshaker.model.enums.Spirit;
import ru.vsu.amm.inshaker.model.item.properties.ItemGroup;
import ru.vsu.amm.inshaker.repositories.PropertiesRepository;

import java.util.Arrays;

@Service
public class PropertiesService {

    private final PropertiesRepository repository;

    public PropertiesService(PropertiesRepository repository) {
        this.repository = repository;
    }

    public CocktailPropertiesDTO getCocktailProperties() {
        CocktailPropertiesDTO properties = new CocktailPropertiesDTO();

        properties.setBases(repository.findDistinctCocktailBases());
        properties.setGroups(repository.findAllDistinct(CocktailGroup.class));
        properties.setSubgroups(repository.findAllDistinct(CocktailSubgroup.class));
        properties.setTastes(repository.findDistinctCocktailTastes());
        properties.setMixingMethods(repository.findAllDistinct(MixingMethod.class));
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

}
