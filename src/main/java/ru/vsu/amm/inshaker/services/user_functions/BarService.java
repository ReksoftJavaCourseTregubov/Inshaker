package ru.vsu.amm.inshaker.services.user_functions;

import org.dozer.Mapper;
import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.model.dto.CocktailSimpleDTO;
import ru.vsu.amm.inshaker.model.dto.IngredientSimpleDTO;
import ru.vsu.amm.inshaker.model.user.User;
import ru.vsu.amm.inshaker.repositories.CocktailRepository;
import ru.vsu.amm.inshaker.repositories.user.UserRepository;
import ru.vsu.amm.inshaker.services.IngredientService;
import ru.vsu.amm.inshaker.services.user.UserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BarService {

    private final IngredientService ingredientService;
    private final CocktailRepository cocktailRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final Mapper mapper;

    public BarService(IngredientService ingredientService,
                      CocktailRepository cocktailRepository,
                      UserRepository userRepository,
                      UserService userService,
                      Mapper mapper) {
        this.ingredientService = ingredientService;
        this.cocktailRepository = cocktailRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.mapper = mapper;
    }

    public Set<IngredientSimpleDTO> getBar() {
        return userService.getCurrentUser().getBar().stream()
                .map(c -> mapper.map(c, IngredientSimpleDTO.class)).collect(Collectors.toSet());
    }

    public void addToBar(Long ingredientId) {
        User currentUser = userService.getCurrentUser();
        currentUser.getBar().add(ingredientService.getIngredient(ingredientId));
        userRepository.save(currentUser);
    }

    public void deleteFromBar(Long ingredientId) {
        User currentUser = userService.getCurrentUser();
        currentUser.getBar().remove(ingredientService.getIngredient(ingredientId));
        userRepository.save(currentUser);
    }

    public List<CocktailSimpleDTO> getAvailableCocktails(Long tolerance) {
        return cocktailRepository.canBeMadeFrom(userService.getCurrentUser().getBar(), tolerance).stream()
                .map(c -> mapper.map(c, CocktailSimpleDTO.class)).collect(Collectors.toList());
    }

}
