package ru.vsu.amm.inshaker.controllers.userfunctions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.amm.inshaker.dto.entire.items.ItemDTO;
import ru.vsu.amm.inshaker.dto.simple.CocktailSimpleDTO;
import ru.vsu.amm.inshaker.services.userfunctions.BarService;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RolesAllowed("ROLE_USER")
public class BarController {

    private final BarService service;

    public BarController(BarService service) {
        this.service = service;
    }

    @PutMapping("items/{id}/add-to-bar")
    public ResponseEntity<String> addToBar(@PathVariable Long id) {
        try {
            boolean isAdded = service.addToBar(id);
            if (isAdded) {
                return ResponseEntity.ok().body("Item " + id + " is added to bar");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Item " + id + " is always in bar");
            }
        } catch (ClassCastException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Item " + id + " is not a correct type");
        }
    }

    @DeleteMapping("items/{id}/remove-from-bar")
    public ResponseEntity<String> removeFromBar(@PathVariable Long id) {
        boolean isRemoved = service.removeFromBar(id);
        if (isRemoved) {
            return ResponseEntity.ok().body("Item " + id + " is removed from bar");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Item " + id + " is not in bar");
        }
    }

    @GetMapping("bar/ingredients")
    public ResponseEntity<List<ItemDTO>> barIngredients() {
        return ResponseEntity.ok(service.getBar());
    }

    @GetMapping("bar/cocktails")
    public ResponseEntity<List<CocktailSimpleDTO>> barCocktails(@RequestParam(required = false) @PositiveOrZero Long tolerance) {
        return ResponseEntity.ok(service.getAvailableCocktails(tolerance));
    }

}
