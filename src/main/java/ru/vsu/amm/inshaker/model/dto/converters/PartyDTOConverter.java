package ru.vsu.amm.inshaker.model.dto.converters;

import org.dozer.Mapper;
import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.model.Cocktail;
import ru.vsu.amm.inshaker.model.Ingredient;
import ru.vsu.amm.inshaker.model.Party;
import ru.vsu.amm.inshaker.model.Recipe;
import ru.vsu.amm.inshaker.model.dto.entire.PartyDTO;
import ru.vsu.amm.inshaker.model.dto.maps.CocktailAmountDTO;
import ru.vsu.amm.inshaker.model.dto.maps.IngredientAmountDTO;
import ru.vsu.amm.inshaker.model.dto.simple.CocktailSimpleDTO;
import ru.vsu.amm.inshaker.model.dto.simple.IngredientSimpleDTO;
import ru.vsu.amm.inshaker.model.dto.simple.PartySimpleDTO;
import ru.vsu.amm.inshaker.services.CocktailService;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PartyDTOConverter {

    private final CocktailService service;
    private final Mapper mapper;

    public PartyDTOConverter(CocktailService service, Mapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    public PartyDTO convert(Party party) {
        hidePasswords(party);
        PartyDTO result = mapper.map(party, PartyDTO.class);
        result.setCocktailAmount(party.getCocktailAmount().entrySet().stream()
                .map(x -> new CocktailAmountDTO(mapper.map(x.getKey(), CocktailSimpleDTO.class), x.getValue()))
                .collect(Collectors.toSet()));

        Map<Ingredient, Short> ingredientMap = new HashMap<>();
        for (Map.Entry<Cocktail, Short> entry : party.getCocktailAmount().entrySet()) {
            for (Recipe recipe : entry.getKey().getRecipe()) {
                ingredientMap.put(recipe.getIngredient(),
                        (short) (ingredientMap.getOrDefault(recipe.getIngredient(), (short) 0)
                                + (recipe.getAmount() == null ? 0 : recipe.getAmount()) * entry.getValue()));
            }
        }

        result.setIngredientAmount(ingredientMap.entrySet().stream()
                .map(x -> new IngredientAmountDTO(mapper.map(x.getKey(), IngredientSimpleDTO.class), x.getValue()))
                .collect(Collectors.toSet()));

        return result;
    }

    public Party convert(PartyDTO party) {
        Party result = mapper.map(party, Party.class);
        result.setCocktailAmount(party.getCocktailAmount().stream()
                .collect(Collectors.toMap(
                        x -> service.getCocktail(x.getCocktail().getId()),
                        CocktailAmountDTO::getAmount
                )));
        return result;
    }

    public PartySimpleDTO convertSimple(Party party) {
        hidePasswords(party);
        PartySimpleDTO result = mapper.map(party, PartySimpleDTO.class);
        result.setCocktailsCount(((short) party.getCocktailAmount().size()));
        return result;
    }

    private void hidePasswords(Party party) {
        try {
            party.getAuthor().setPassword(null);
            party.getMembers().forEach(m -> m.setPassword(null));
        } catch (NullPointerException ignored) {
        }
    }

}
