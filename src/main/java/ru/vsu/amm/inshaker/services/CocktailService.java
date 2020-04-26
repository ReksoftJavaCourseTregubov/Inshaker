package ru.vsu.amm.inshaker.services;

import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.exceptions.AnonymousAuthenticationException;
import ru.vsu.amm.inshaker.exceptions.CocktailNotFoundException;
import ru.vsu.amm.inshaker.model.Cocktail;
import ru.vsu.amm.inshaker.model.dto.CocktailDTO;
import ru.vsu.amm.inshaker.model.dto.CocktailDTOConverter;
import ru.vsu.amm.inshaker.model.dto.CocktailSimpleDTO;
import ru.vsu.amm.inshaker.model.enums.Spirit;
import ru.vsu.amm.inshaker.model.user.User;
import ru.vsu.amm.inshaker.repositories.CocktailRepository;
import ru.vsu.amm.inshaker.repositories.user.UserRepository;
import ru.vsu.amm.inshaker.services.user.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CocktailService {

    private final CocktailRepository cocktailRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final Mapper mapper;
    private final CocktailDTOConverter cocktailDTOConverter;

    public CocktailService(CocktailRepository cocktailRepository,
                           UserRepository userRepository,
                           UserService userService,
                           Mapper mapper,
                           CocktailDTOConverter cocktailDTOConverter) {
        this.cocktailRepository = cocktailRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.mapper = mapper;
        this.cocktailDTOConverter = cocktailDTOConverter;
    }


    public Cocktail getCocktail(Long id) {
        return cocktailRepository.findById(id)
                .orElseThrow(() -> new CocktailNotFoundException(id));
    }

    public List<Cocktail> getAllCocktails(User author, String search, String base, String spirit, String group, List<String> tastes) {
        if (author == null && search == null && base == null && spirit == null && group == null && tastes == null) {
            return cocktailRepository.findAllByAuthorIsNull();
        }

        Spirit s = Spirit.findByRuName(spirit);
        if (tastes == null) {
            return cocktailRepository.findAllWithFilters(author, search, base, s.getRangeLow(), s.getRangeHigh(), group);
        } else {
            return cocktailRepository.findAllWithFilters(author, search, base, s.getRangeLow(), s.getRangeHigh(), group, tastes, (long) tastes.size());
        }
    }


    public CocktailDTO get(Long id) {
        Cocktail cocktail = getCocktail(id);
        CocktailDTO cocktailDTO = mapper.map(cocktail, CocktailDTO.class);

        try {
            if (userService.getCurrentUser().getFavorite().contains(cocktail)) {
                cocktailDTO.setFavorite(true);
            }
        } catch (AnonymousAuthenticationException ignored) {
        }

        return cocktailDTO;
    }

    public List<CocktailSimpleDTO> getAll(String search, String base, String spirit, String group, List<String> tastes) {
        return getAllCocktails(null, search, base, spirit, group, tastes).stream()
                .map(c -> mapper.map(c, CocktailSimpleDTO.class)).collect(Collectors.toList());
    }

    public List<CocktailSimpleDTO> getPopular(int limit) {
        return userRepository.findPopularCocktails(PageRequest.of(0, limit)).stream()
                .map(c -> mapper.map(c, CocktailSimpleDTO.class)).collect(Collectors.toList());
    }


    public CocktailDTO add(CocktailDTO cocktail) {
        Cocktail newCocktail = cocktailDTOConverter.convert(cocktail);
        newCocktail.setId(null);
        newCocktail.setAuthor(null);
        return mapper.map(cocktailRepository.save(newCocktail), CocktailDTO.class);
    }

    public CocktailDTO update(CocktailDTO newCocktail, Long id) {
        return cocktailRepository.findById(id)
                .map(oldCocktail -> {
                    BeanUtils.copyProperties(cocktailDTOConverter.convert(newCocktail), oldCocktail);
                    oldCocktail.setId(id);
                    return mapper.map(cocktailRepository.save(oldCocktail), CocktailDTO.class);
                }).orElseThrow(() -> new CocktailNotFoundException(id));
    }

    public void delete(Long id) {
        try {
            cocktailRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new CocktailNotFoundException(id);
        }
    }


    public Set<String> getBases() {
        return cocktailRepository.findDistinctBases();
    }

    public Set<String> getCocktailsGroups() {
        return cocktailRepository.findDistinctCocktailGroups();
    }

    public Set<String> getTastes() {
        return cocktailRepository.findDistinctTastes();
    }

    public Set<String> getSpirits() {
        return Arrays.stream(Spirit.values()).map(Spirit::getRuName).collect(Collectors.toSet());
    }
    
}
