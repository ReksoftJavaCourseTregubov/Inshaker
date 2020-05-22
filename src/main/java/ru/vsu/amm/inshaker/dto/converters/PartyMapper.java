package ru.vsu.amm.inshaker.dto.converters;

import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.dto.entire.PartyDTO;
import ru.vsu.amm.inshaker.dto.maps.CocktailAmountDTO;
import ru.vsu.amm.inshaker.dto.maps.IngredientAmountDTO;
import ru.vsu.amm.inshaker.dto.simple.ItemSimpleDTO;
import ru.vsu.amm.inshaker.dto.simple.PartySimpleDTO;
import ru.vsu.amm.inshaker.dto.simple.TablewareSimpleDTO;
import ru.vsu.amm.inshaker.model.Party;
import ru.vsu.amm.inshaker.model.RecipePart;
import ru.vsu.amm.inshaker.model.cocktail.Cocktail;
import ru.vsu.amm.inshaker.model.item.Ingredient;
import ru.vsu.amm.inshaker.services.CocktailService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PartyMapper {

    private final CocktailService service;
    private final Mapper mapper;
    private final CocktailMapper cocktailMapper;

    public PartyMapper(CocktailService service, Mapper mapper, CocktailMapper cocktailMapper) {
        this.service = service;
        this.mapper = mapper;
        this.cocktailMapper = cocktailMapper;
    }

    public PartyDTO map(Party party) {
        PartyDTO result = mapper.map(party, PartyDTO.class);

        Map<Ingredient, Short> ingredientMap = new HashMap<>();
        result.setTableware(new HashSet<>());

        for (Map.Entry<Cocktail, Short> entry : party.getCocktailAmount().entrySet()) {
            for (RecipePart recipePart : entry.getKey().getRecipePart()) {
                ingredientMap.put(recipePart.getIngredient(),
                        (short) (ingredientMap.getOrDefault(recipePart.getIngredient(), (short) 0)
                                + (recipePart.getAmount() == null ? 0 : recipePart.getAmount()) * entry.getValue()));
            }

            if (entry.getKey().getGarnish() != null) {
                ingredientMap.put(entry.getKey().getGarnish(), (short) 0);
            }

            result.getCocktailAmount().add(new CocktailAmountDTO(cocktailMapper.mapSimple(entry.getKey()), entry.getValue()));

            if (entry.getKey().getGlass() != null) {
                result.getTableware().add(mapper.map(entry.getKey().getGlass(), TablewareSimpleDTO.class));
            }

            result.getTableware().addAll(entry.getKey().getMixingMethod().getTableware()
                    .stream()
                    .filter(Objects::nonNull)
                    .map(t -> mapper.map(t, TablewareSimpleDTO.class))
                    .collect(Collectors.toSet()));
        }

        result.setIngredientAmount(ingredientMap.entrySet()
                .stream()
                .map(x -> new IngredientAmountDTO(mapper.map(x.getKey(), ItemSimpleDTO.class), x.getValue()))
                .sorted(Comparator.comparing(IngredientAmountDTO::getAmount).reversed())
                .collect(Collectors.toList()));

        return result;
    }

    public Party map(PartyDTO source, Party destination) {
        BeanUtils.copyProperties(source, destination, "cocktailAmount", "members");
        destination.setCocktailAmount(Optional.ofNullable(source.getCocktailAmount())
                .map(t -> t.stream()
                        .collect(Collectors.toMap(
                                x -> service.getCocktail(x.getCocktail().getId()),
                                CocktailAmountDTO::getAmount
                        )))
                .orElse(Collections.emptyMap()));
        return destination;
    }

    public PartySimpleDTO mapSimple(Party party) {
        PartySimpleDTO result = mapper.map(party, PartySimpleDTO.class);
        result.setCocktailsCount(((short) party.getCocktailAmount().size()));
        return result;
    }

}
