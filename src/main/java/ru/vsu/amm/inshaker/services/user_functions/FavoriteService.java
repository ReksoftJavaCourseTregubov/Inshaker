package ru.vsu.amm.inshaker.services.user_functions;

import org.dozer.Mapper;
import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.model.dto.CocktailSimpleDTO;
import ru.vsu.amm.inshaker.model.user.User;
import ru.vsu.amm.inshaker.repositories.user.UserRepository;
import ru.vsu.amm.inshaker.services.CocktailService;
import ru.vsu.amm.inshaker.services.user.UserService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final CocktailService cocktailService;
    private final Mapper mapper;

    public FavoriteService(UserRepository userRepository,
                           UserService userService,
                           CocktailService cocktailService,
                           Mapper mapper) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.cocktailService = cocktailService;
        this.mapper = mapper;
    }

    public Set<CocktailSimpleDTO> getFavorites() {
        return userService.getCurrentUser().getFavorite().stream()
                .map(c -> mapper.map(c, CocktailSimpleDTO.class)).collect(Collectors.toSet());
    }

    public void addToFavorite(Long cocktailId) {
        User currentUser = userService.getCurrentUser();
        currentUser.getFavorite().add(cocktailService.getCocktail(cocktailId));
        userRepository.save(currentUser);
    }

    public void deleteFromFavorite(Long cocktailId) {
        User currentUser = userService.getCurrentUser();
        currentUser.getFavorite().remove(cocktailService.getCocktail(cocktailId));
        userRepository.save(currentUser);
    }

}
