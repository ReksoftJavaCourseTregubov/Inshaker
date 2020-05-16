package ru.vsu.amm.inshaker.dto.converters;

import org.dozer.Mapper;
import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.dto.converters.items.ItemMapper;
import ru.vsu.amm.inshaker.dto.entire.PartyDTO;
import ru.vsu.amm.inshaker.dto.maps.CocktailAmountDTO;
import ru.vsu.amm.inshaker.dto.maps.IngredientAmountDTO;
import ru.vsu.amm.inshaker.dto.simple.PartySimpleDTO;
import ru.vsu.amm.inshaker.dto.simple.TablewareDTO;
import ru.vsu.amm.inshaker.model.Party;
import ru.vsu.amm.inshaker.model.RecipePart;
import ru.vsu.amm.inshaker.model.cocktail.Cocktail;
import ru.vsu.amm.inshaker.model.item.Ingredient;
import ru.vsu.amm.inshaker.model.item.Item;
import ru.vsu.amm.inshaker.services.CocktailService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PartyMapper {

    private final CocktailService service;
    private final Mapper mapper;
    private final CocktailMapper cocktailMapper;
    private final ItemMapper<Item> itemMapper;

    public PartyMapper(CocktailService service, Mapper mapper, CocktailMapper cocktailMapper, ItemMapper<Item> itemMapper) {
        this.service = service;
        this.mapper = mapper;
        this.cocktailMapper = cocktailMapper;
        this.itemMapper = itemMapper;
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

            result.getTableware().add(mapper.map(entry.getKey().getGlass(), TablewareDTO.class));
            result.getTableware().addAll(entry.getKey().getMixingMethod().getTableware()
                    .stream()
                    .map(t -> mapper.map(t, TablewareDTO.class))
                    .collect(Collectors.toSet()));
        }

        result.setIngredientAmount(ingredientMap.entrySet()
                .stream()
                .map(x -> new IngredientAmountDTO(itemMapper.map(x.getKey()), x.getValue()))
                .sorted(Comparator.comparing(IngredientAmountDTO::getAmount).reversed())
                .collect(Collectors.toList()));

        return result;
    }

    public Party map(PartyDTO party) {
        Party result = mapper.map(party, Party.class);
        result.setCocktailAmount(party.getCocktailAmount()
                .stream()
                .collect(Collectors.toMap(
                        x -> service.getCocktail(x.getCocktail().getId()),
                        CocktailAmountDTO::getAmount
                )));
        return result;
    }

    public PartySimpleDTO mapSimple(Party party) {
        PartySimpleDTO result = mapper.map(party, PartySimpleDTO.class);
        result.setCocktailsCount(((short) party.getCocktailAmount().size()));
        return result;
    }

}
