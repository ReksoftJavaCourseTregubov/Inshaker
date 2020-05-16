package ru.vsu.amm.inshaker.services.userfunctions;

import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.dto.converters.CocktailMapper;
import ru.vsu.amm.inshaker.dto.simple.CocktailSimpleDTO;
import ru.vsu.amm.inshaker.model.user.User;
import ru.vsu.amm.inshaker.repositories.user.UserRepository;
import ru.vsu.amm.inshaker.services.CocktailService;
import ru.vsu.amm.inshaker.services.user.UserService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoritesService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final CocktailService cocktailService;
    private final CocktailMapper mapper;

    public FavoritesService(UserRepository userRepository,
                            UserService userService,
                            CocktailService cocktailService,
                            CocktailMapper mapper) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.cocktailService = cocktailService;
        this.mapper = mapper;
    }

    public List<CocktailSimpleDTO> getFavorites() {
        return userService.getCurrentUser().getFavorite()
                .stream()
                .map(mapper::mapSimple)
                .sorted(Comparator.comparing(CocktailSimpleDTO::getNameRu))
                .collect(Collectors.toList());
    }

    public boolean addToFavorites(Long cocktailId) {
        User currentUser = userService.getCurrentUser();
        boolean isAdded = currentUser.getFavorite().add(cocktailService.getCocktail(cocktailId));
        if (isAdded) {
            userRepository.save(currentUser);
        }
        return isAdded;
    }

    public boolean removeFromFavorites(Long cocktailId) {
        User currentUser = userService.getCurrentUser();
        boolean isRemoved = currentUser.getFavorite().remove(cocktailService.getCocktail(cocktailId));
        if (isRemoved) {
            userRepository.save(currentUser);
        }
        return isRemoved;
    }

}
