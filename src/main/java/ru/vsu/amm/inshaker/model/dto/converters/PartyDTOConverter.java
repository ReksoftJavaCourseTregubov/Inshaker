package ru.vsu.amm.inshaker.model.dto.converters;

import org.dozer.Mapper;
import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.model.Party;
import ru.vsu.amm.inshaker.model.dto.CocktailSimpleDTO;
import ru.vsu.amm.inshaker.model.dto.PartyDTO;
import ru.vsu.amm.inshaker.model.dto.PartySimpleDTO;

import java.util.stream.Collectors;

@Service
public class PartyDTOConverter {

    private final Mapper mapper;

    public PartyDTOConverter(Mapper mapper) {
        this.mapper = mapper;
    }

    public PartyDTO convertTo(Party party) {
        PartyDTO result = mapper.map(party, PartyDTO.class);
        result.setCocktailAmount(party.getCocktailAmount().keySet().stream()
                .map(i -> mapper.map(i, CocktailSimpleDTO.class))
                .collect(Collectors.toList()));
        return result;
    }

    public Party convertFrom(PartyDTO party) {
        return mapper.map(party, Party.class);
    }

    public PartySimpleDTO convertToSimple(Party party) {
        PartySimpleDTO result = mapper.map(party, PartySimpleDTO.class);
        result.setCocktailsCount(((short) party.getCocktailAmount().size()));
        return result;
    }

}
