package ru.vsu.amm.inshaker.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.dto.converters.CocktailMapper;
import ru.vsu.amm.inshaker.dto.entire.CocktailDTO;
import ru.vsu.amm.inshaker.dto.properties.CocktailPropertiesDTO;
import ru.vsu.amm.inshaker.dto.simple.CocktailSimpleDTO;
import ru.vsu.amm.inshaker.exceptions.AnonymousAuthenticationException;
import ru.vsu.amm.inshaker.exceptions.notfound.CocktailNotFoundException;
import ru.vsu.amm.inshaker.model.cocktail.Cocktail;
import ru.vsu.amm.inshaker.model.cocktail.MixingMethod;
import ru.vsu.amm.inshaker.model.user.User;
import ru.vsu.amm.inshaker.repositories.CocktailRepository;
import ru.vsu.amm.inshaker.repositories.SearchRepository;
import ru.vsu.amm.inshaker.repositories.user.UserRepository;
import ru.vsu.amm.inshaker.services.user.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CocktailService {

    private final CocktailRepository cocktailRepository;
    private final SearchRepository searchRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final PropertiesService propertiesService;
    private final CocktailMapper mapper;

    public CocktailService(CocktailRepository cocktailRepository,
                           SearchRepository searchRepository,
                           UserRepository userRepository,
                           UserService userService,
                           PropertiesService propertiesService,
                           CocktailMapper mapper) {
        this.cocktailRepository = cocktailRepository;
        this.searchRepository = searchRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.propertiesService = propertiesService;
        this.mapper = mapper;
    }

    public Cocktail getCocktail(Long id) {
        return cocktailRepository.findByIdAndAuthorIsNull(id)
                .orElseThrow(() -> new CocktailNotFoundException(id));
    }

    public CocktailDTO getOne(Long id) {
        Cocktail cocktail = getCocktail(id);
        CocktailDTO cocktailDTO = mapper.map(cocktail);

        try {
            if (userService.getCurrentUser().getFavorite().contains(cocktail)) {
                cocktailDTO.setFavorite(true);
            }
        } catch (AnonymousAuthenticationException ignored) {
        }

        return cocktailDTO;
    }

    public List<CocktailSimpleDTO> getAll() {
        return cocktailRepository.findAllByAuthorIsNull()
                .stream()
                .map(mapper::mapSimple)
                .collect(Collectors.toList());
    }

    public List<CocktailSimpleDTO> getAll(String search, Long baseId, Long groupId, Long subgroupId,
                                          Long spiritId, Long mixingMethodId, List<Long> tasteIds) {
        return searchRepository
                .searchCocktails(search, baseId, groupId, subgroupId, spiritId, mixingMethodId, tasteIds)
                .stream()
                .map(mapper::mapSimple)
                .collect(Collectors.toList());
    }

    public List<CocktailSimpleDTO> getPopular(int limit) {
        return userRepository.findPopularCocktails(PageRequest.of(0, limit)).stream()
                .map(mapper::mapSimple).collect(Collectors.toList());
    }

    public CocktailPropertiesDTO getProperties() {
        return propertiesService.getCocktailProperties();
    }

    public MixingMethod getMixingMethod(Long id) {
        return propertiesService.getMixingMethod(id);
    }


    public CocktailDTO addCocktail(CocktailDTO cocktail, User author) {
        cocktail.setId(null);
        Cocktail newCocktail = new Cocktail();
        mapper.map(cocktail, newCocktail);
        newCocktail.setAuthor(author);
        return mapper.map(cocktailRepository.save(newCocktail));
    }

    public CocktailDTO updateCocktail(CocktailDTO newCocktail, Long id, User author) {
        return cocktailRepository.findByIdAndAuthor(id, author)
                .map(oldCocktail -> {
                    newCocktail.setId(id);
                    mapper.map(newCocktail, oldCocktail);
                    oldCocktail.setAuthor(author);
                    return mapper.map(cocktailRepository.save(oldCocktail));
                }).orElseThrow(() -> new CocktailNotFoundException(id));
    }

    public void deleteCocktail(Long id, User author) {
        if (cocktailRepository.existsByIdAndAuthor(id, author)) {
            cocktailRepository.deletePartyCocktailAmountByCocktailId(id);
            cocktailRepository.deleteUserFavoriteByCocktailId(id);
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

}
