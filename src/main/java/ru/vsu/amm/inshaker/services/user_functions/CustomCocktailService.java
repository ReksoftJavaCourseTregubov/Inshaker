package ru.vsu.amm.inshaker.services.user_functions;

import org.dozer.Mapper;
import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.exceptions.CocktailNotFoundException;
import ru.vsu.amm.inshaker.model.dto.CocktailDTO;
import ru.vsu.amm.inshaker.model.dto.CocktailSimpleDTO;
import ru.vsu.amm.inshaker.repositories.CocktailRepository;
import ru.vsu.amm.inshaker.services.CocktailService;
import ru.vsu.amm.inshaker.services.user.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomCocktailService {

    private final CocktailRepository cocktailRepository;
    private final CocktailService cocktailService;
    private final UserService userService;
    private final Mapper mapper;

    public CustomCocktailService(CocktailRepository cocktailRepository,
                                 CocktailService cocktailService,
                                 UserService userService,
                                 Mapper mapper) {
        this.cocktailRepository = cocktailRepository;
        this.cocktailService = cocktailService;
        this.userService = userService;
        this.mapper = mapper;
    }

    public CocktailDTO getCustom(Long id) {
        return mapper.map(cocktailRepository.findByIdAndAuthor(id, userService.getCurrentUser())
                .orElseThrow(() -> new CocktailNotFoundException(id)), CocktailDTO.class);
    }

    public List<CocktailSimpleDTO> getAllCustoms(String search, String base, String spirit, String group, List<String> tastes) {
        return cocktailService.getAllCocktails(userService.getCurrentUser(), search, base, spirit, group, tastes).stream()
                .map(c -> mapper.map(c, CocktailSimpleDTO.class)).collect(Collectors.toList());
    }

    public CocktailDTO addCustom(CocktailDTO cocktail) {
        return cocktailService.addCocktail(cocktail, userService.getCurrentUser());
    }

    public CocktailDTO updateCustom(CocktailDTO newCocktail, Long id) {
        return cocktailService.updateCocktail(newCocktail, id, userService.getCurrentUser());
    }

    public void deleteCustom(Long id) {
        cocktailService.deleteCocktail(id, userService.getCurrentUser());
    }

}
