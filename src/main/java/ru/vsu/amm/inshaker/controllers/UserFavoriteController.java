package ru.vsu.amm.inshaker.controllers;

import org.springframework.web.bind.annotation.*;
import ru.vsu.amm.inshaker.model.dto.CocktailSimpleDTO;
import ru.vsu.amm.inshaker.services.CocktailService;

import javax.annotation.security.RolesAllowed;
import java.util.Set;

@RestController
public class UserFavoriteController {

    private final CocktailService cocktailService;

    public UserFavoriteController(CocktailService cocktailService) {
        this.cocktailService = cocktailService;
    }

    @RolesAllowed("ROLE_USER")
    @GetMapping("user/favorites")
    public Set<CocktailSimpleDTO> favorites() {
        return cocktailService.getFavorites();
    }

    @RolesAllowed("ROLE_USER")
    @PostMapping("user/favorites")
    public void addToFavorites(@RequestBody Long id) {
        cocktailService.addToFavorite(id);
    }

    @RolesAllowed("ROLE_USER")
    @DeleteMapping("user/favorites/{id}")
    public void deleteFromFavorites(@PathVariable("id") Long id) {
        cocktailService.deleteFromFavorite(id);
    }

}
