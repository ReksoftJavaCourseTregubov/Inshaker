package ru.vsu.amm.inshaker.controllers.user_functions;

import org.springframework.web.bind.annotation.*;
import ru.vsu.amm.inshaker.model.dto.CocktailSimpleDTO;
import ru.vsu.amm.inshaker.services.user_functions.FavoriteService;

import javax.annotation.security.RolesAllowed;
import java.util.Set;

@RestController
@RolesAllowed("ROLE_USER")
@RequestMapping("/user/favorites")
public class UserFavoriteController {

    private final FavoriteService service;

    public UserFavoriteController(FavoriteService service) {
        this.service = service;
    }

    @GetMapping
    public Set<CocktailSimpleDTO> favorites() {
        return service.getFavorites();
    }

    @PostMapping
    public void addToFavorites(@RequestBody Long id) {
        service.addToFavorite(id);
    }

    @DeleteMapping("/{id}")
    public void deleteFromFavorites(@PathVariable("id") Long id) {
        service.deleteFromFavorite(id);
    }

}
