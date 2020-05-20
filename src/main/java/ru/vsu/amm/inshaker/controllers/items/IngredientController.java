package ru.vsu.amm.inshaker.controllers.items;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.amm.inshaker.dto.entire.items.IngredientDTO;
import ru.vsu.amm.inshaker.model.item.Ingredient;
import ru.vsu.amm.inshaker.services.items.IngredientService;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("/ingredients")
public class IngredientController<T extends Ingredient, S extends IngredientDTO> extends ItemController<T, S> {

    public IngredientController(IngredientService<T, S> ingredientService) {
        super(ingredientService);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<S> one(@PathVariable Long id) {
        return super.one(id);
    }

    @Override
    @RolesAllowed("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<S> add(@RequestBody @Valid S item) {
        return super.add(item);
    }

    @Override
    @RolesAllowed("ROLE_ADMIN")
    @PutMapping("/{id}")
    public ResponseEntity<S> update(@PathVariable Long id, @RequestBody @Valid S item) {
        return super.update(id, item);
    }

}
