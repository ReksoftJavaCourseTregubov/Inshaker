package ru.vsu.amm.inshaker.controllers;

import org.springframework.web.bind.annotation.*;
import ru.vsu.amm.inshaker.model.dto.CocktailDTO;
import ru.vsu.amm.inshaker.model.dto.CocktailSimpleDTO;
import ru.vsu.amm.inshaker.model.dto.IngredientSimpleDTO;
import ru.vsu.amm.inshaker.services.BarService;
import ru.vsu.amm.inshaker.services.CocktailService;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Set;

@RestController
public class UserFunctionsController {

    private final CocktailService cocktailService;
    private final BarService barService;

    public UserFunctionsController(CocktailService cocktailService, BarService barService) {
        this.cocktailService = cocktailService;
        this.barService = barService;
    }

    @RolesAllowed("ROLE_USER")
    @GetMapping("/user/favorites")
    public Set<CocktailSimpleDTO> favorites() {
        return cocktailService.getFavorites();
    }

    @RolesAllowed("ROLE_USER")
    @PostMapping("/user/favorites")
    public void addToFavorites(@RequestBody Long id) {
        cocktailService.addToFavorite(id);
    }

    @RolesAllowed("ROLE_USER")
    @DeleteMapping("/user/favorites/{id}")
    public void deleteFromFavorites(@PathVariable("id") Long id) {
        cocktailService.deleteFromFavorite(id);
    }

    @RolesAllowed("ROLE_USER")
    @GetMapping("/user/bar")
    public Set<IngredientSimpleDTO> bar() {
        return barService.getBar();
    }

    @RolesAllowed("ROLE_USER")
    @PostMapping("/user/bar")
    public void addToBar(@RequestBody Long id) {
        barService.addToBar(id);
    }

    @RolesAllowed("ROLE_USER")
    @DeleteMapping("/user/bar/{id}")
    public void deleteFromBar(@PathVariable("id") Long id) {
        barService.deleteFromBar(id);
    }

    @RolesAllowed("ROLE_USER")
    @GetMapping("/user/cocktails")
    public List<CocktailSimpleDTO> all(@RequestParam(required = false) String search,
                                       @RequestParam(required = false) String base,
                                       @RequestParam(required = false) String spirit,
                                       @RequestParam(required = false) String group,
                                       @RequestParam(required = false) List<String> tastes) {
        return cocktailService.getAllCustoms(search, base, spirit, group, tastes);
    }

    @RolesAllowed("ROLE_USER")
    @PostMapping("/user/cocktails")
    public CocktailDTO add(@RequestBody CocktailDTO cocktail) {
        return cocktailService.addCustom(cocktail);
    }

    @RolesAllowed("ROLE_USER")
    @GetMapping("/user/cocktails/{id}")
    public CocktailDTO one(@PathVariable Long id) {
        return cocktailService.getCustom(id);
    }

    @RolesAllowed("ROLE_USER")
    @PutMapping("/user/cocktails/{id}")
    CocktailDTO update(@RequestBody CocktailDTO cocktail, @PathVariable Long id) {
        return cocktailService.updateCustom(cocktail, id);
    }

    @RolesAllowed("ROLE_USER")
    @DeleteMapping("/user/cocktails/{id}")
    public void delete(@PathVariable Long id) {
        cocktailService.deleteCustom(id);
    }

}
