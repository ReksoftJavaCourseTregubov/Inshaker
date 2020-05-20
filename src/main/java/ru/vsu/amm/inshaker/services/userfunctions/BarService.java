package ru.vsu.amm.inshaker.services.userfunctions;

import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.dto.converters.CocktailMapper;
import ru.vsu.amm.inshaker.dto.converters.items.ItemMapper;
import ru.vsu.amm.inshaker.dto.entire.items.IngredientDTO;
import ru.vsu.amm.inshaker.dto.entire.items.ItemDTO;
import ru.vsu.amm.inshaker.dto.simple.CocktailSimpleDTO;
import ru.vsu.amm.inshaker.model.item.Ingredient;
import ru.vsu.amm.inshaker.model.user.User;
import ru.vsu.amm.inshaker.repositories.CocktailRepository;
import ru.vsu.amm.inshaker.repositories.user.UserRepository;
import ru.vsu.amm.inshaker.services.items.IngredientService;
import ru.vsu.amm.inshaker.services.user.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BarService {

    private final IngredientService<? extends Ingredient, ? extends IngredientDTO> ingredientService;
    private final CocktailRepository cocktailRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ItemMapper<Ingredient, IngredientDTO> itemMapper;
    private final CocktailMapper cocktailMapper;

    public BarService(IngredientService<Ingredient, IngredientDTO> ingredientService,
                      CocktailRepository cocktailRepository,
                      UserRepository userRepository,
                      UserService userService,
                      ItemMapper<Ingredient, IngredientDTO> itemMapper,
                      CocktailMapper cocktailMapper) {
        this.ingredientService = ingredientService;
        this.cocktailRepository = cocktailRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.itemMapper = itemMapper;
        this.cocktailMapper = cocktailMapper;
    }

    public List<ItemDTO> getBar() {
        return userService.getCurrentUser().getBar().stream()
                .map(itemMapper::map).collect(Collectors.toList());
    }

    public boolean addToBar(Long ingredientId) {
        User currentUser = userService.getCurrentUser();
        boolean isAdded = currentUser.getBar().add(ingredientService.getItem(ingredientId));
        if (isAdded) {
            userRepository.save(currentUser);
        }
        return isAdded;
    }

    public boolean removeFromBar(Long ingredientId) {
        User currentUser = userService.getCurrentUser();
        boolean isRemoved = currentUser.getBar().remove(ingredientService.getItem(ingredientId));
        if (isRemoved) {
            userRepository.save(currentUser);
        }
        return isRemoved;
    }

    public List<CocktailSimpleDTO> getAvailableCocktails(Long tolerance) {
        return cocktailRepository.canBeMadeFrom(userService.getCurrentUser().getBar(), tolerance)
                .stream()
                .map(cocktailMapper::mapSimple)
                .collect(Collectors.toList());
    }

}
