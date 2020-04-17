package ru.vsu.amm.inshaker.services;

import org.dozer.Mapper;
import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.exceptions.AnonymousAuthenticationException;
import ru.vsu.amm.inshaker.exceptions.CocktailNotFoundException;
import ru.vsu.amm.inshaker.model.Cocktail;
import ru.vsu.amm.inshaker.model.dto.CocktailDTO;
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

    public CocktailService(CocktailRepository cocktailRepository, UserRepository userRepository, UserService userService, Mapper mapper) {
        this.cocktailRepository = cocktailRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.mapper = mapper;
    }

    public List<Cocktail> getAllCocktails(String search, String base, String spirit, String group, List<String> tastes) {
        if (search == null && base == null && spirit == null && group == null && tastes == null) {
            return cocktailRepository.findAll();
        }

        Spirit s = Spirit.findByRuName(spirit);
        if (tastes == null) {
            return cocktailRepository.findAllWithFilters(search, base, s.getRangeLow(), s.getRangeHigh(), group);
        } else {
            return cocktailRepository.findAllWithFilters(search, base, s.getRangeLow(), s.getRangeHigh(), group, tastes, (long) tastes.size());
        }
    }

    public List<CocktailSimpleDTO> getAll(String search, String base, String spirit, String group, List<String> tastes) {
        return getAllCocktails(search, base, spirit, group, tastes).stream()
                .map(c -> mapper.map(c, CocktailSimpleDTO.class)).collect(Collectors.toList());
    }

    public Cocktail getCocktail(Long id) {
        return cocktailRepository.findById(id)
                .orElseThrow(() -> new CocktailNotFoundException(id));
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

    public Set<CocktailSimpleDTO> getFavorites() {
        return userService.getCurrentUser().getFavorite().stream()
                .map(c -> mapper.map(c, CocktailSimpleDTO.class)).collect(Collectors.toSet());
    }

    public void addToFavorite(Long cocktailId) {
        User currentUser = userService.getCurrentUser();
        currentUser.getFavorite().add(getCocktail(cocktailId));
        userRepository.save(currentUser);
    }

    public void deleteFromFavorite(Long cocktailId) {
        User currentUser = userService.getCurrentUser();
        currentUser.getFavorite().remove(getCocktail(cocktailId));
        userRepository.save(currentUser);
    }

}
