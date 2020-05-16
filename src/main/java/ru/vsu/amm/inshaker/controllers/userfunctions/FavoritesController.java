package ru.vsu.amm.inshaker.controllers.userfunctions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.amm.inshaker.dto.simple.CocktailSimpleDTO;
import ru.vsu.amm.inshaker.services.userfunctions.FavoritesService;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RolesAllowed("ROLE_USER")
@RequestMapping("/cocktails")
public class FavoritesController {

    private final FavoritesService service;

    public FavoritesController(FavoritesService service) {
        this.service = service;
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<CocktailSimpleDTO>> favorites() {
        return ResponseEntity.ok(service.getFavorites());
    }

    @PutMapping("/{id}/add-to-favorites")
    public ResponseEntity<String> addToFavorites(@PathVariable Long id) {
        boolean isAdded = service.addToFavorites(id);
        if (isAdded) {
            return ResponseEntity.ok().body("Cocktail " + id + " is added to favorites");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cocktail " + id + " is always in favorites");
        }
    }

    @DeleteMapping("/{id}/remove-from-favorites")
    public ResponseEntity<String> removeFromFavorites(@PathVariable("id") Long id) {
        boolean isRemoved = service.removeFromFavorites(id);
        if (isRemoved) {
            return ResponseEntity.ok().body("Cocktail " + id + " is removed from favorites");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cocktail " + id + " is not in favorites");
        }
    }

}
