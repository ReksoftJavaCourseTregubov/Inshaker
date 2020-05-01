package ru.vsu.amm.inshaker.services;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.exceptions.AnonymousAuthenticationException;
import ru.vsu.amm.inshaker.exceptions.entity_not_foound_exceptions.CocktailNotFoundException;
import ru.vsu.amm.inshaker.model.Cocktail;
import ru.vsu.amm.inshaker.model.dto.converters.CocktailDTOConverter;
import ru.vsu.amm.inshaker.model.dto.entire.CocktailDTO;
import ru.vsu.amm.inshaker.model.dto.simple.CocktailSimpleDTO;
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
    private final CocktailDTOConverter converter;

    public CocktailService(CocktailRepository cocktailRepository,
                           UserRepository userRepository,
                           UserService userService,
                           CocktailDTOConverter converter) {
        this.cocktailRepository = cocktailRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.converter = converter;
    }


    public Cocktail getCocktail(Long id) {
        return cocktailRepository.findByIdAndAuthorIsNull(id)
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
        CocktailDTO cocktailDTO = converter.convert(cocktail);

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
                .map(converter::convertSimple).collect(Collectors.toList());
    }

    public List<CocktailSimpleDTO> getPopular(int limit) {
        return userRepository.findPopularCocktails(PageRequest.of(0, limit)).stream()
                .map(converter::convertSimple).collect(Collectors.toList());
    }


    public CocktailDTO addCocktail(CocktailDTO cocktail, User author) {
        Cocktail newCocktail = converter.convert(cocktail);
        newCocktail.setId(null);
        newCocktail.setAuthor(author);
        return converter.convert(cocktailRepository.save(newCocktail));
    }

    public CocktailDTO updateCocktail(CocktailDTO newCocktail, Long id, User author) {
        return cocktailRepository.findByIdAndAuthor(id, author)
                .map(oldCocktail -> {
                    BeanUtils.copyProperties(converter.convert(newCocktail), oldCocktail);
                    oldCocktail.setId(id);
                    oldCocktail.setAuthor(author);
                    return converter.convert(cocktailRepository.save(oldCocktail));
                }).orElseThrow(() -> new CocktailNotFoundException(id));
    }

    public void deleteCocktail(Long id, User author) {
        if (cocktailRepository.existsByIdAndAuthor(id, author)) {
            cocktailRepository.deleteById(id);
        } else throw new CocktailNotFoundException(id);
    }


    public CocktailDTO add(CocktailDTO cocktail) {
        return addCocktail(cocktail, null);
    }

    public CocktailDTO update(CocktailDTO newCocktail, Long id) {
        return updateCocktail(newCocktail, id, null);
    }

    public void delete(Long id) {
        deleteCocktail(id, null);
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
