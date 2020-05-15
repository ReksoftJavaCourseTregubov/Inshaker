package ru.vsu.amm.inshaker.services.user_functions;

import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.dto.converters.CocktailMapper;
import ru.vsu.amm.inshaker.dto.entire.CocktailDTO;
import ru.vsu.amm.inshaker.dto.simple.CocktailSimpleDTO;
import ru.vsu.amm.inshaker.exceptions.notfound.CocktailNotFoundException;
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
    private final CocktailMapper mapper;

    public CustomCocktailService(CocktailRepository cocktailRepository,
                                 CocktailService cocktailService,
                                 UserService userService,
                                 CocktailMapper mapper) {
        this.cocktailRepository = cocktailRepository;
        this.cocktailService = cocktailService;
        this.userService = userService;
        this.mapper = mapper;
    }

    public CocktailDTO getOneCustom(Long id) {
        return mapper.map(cocktailRepository.findByIdAndAuthor(id, userService.getCurrentUser())
                .orElseThrow(() -> new CocktailNotFoundException(id)));
    }

    public List<CocktailSimpleDTO> getAllCustom() {
        return cocktailRepository.findAllByAuthor(userService.getCurrentUser())
                .stream()
                .map(mapper::mapSimple)
                .collect(Collectors.toList());
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
