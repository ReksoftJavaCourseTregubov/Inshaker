package ru.vsu.amm.inshaker.controllers.items;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.amm.inshaker.model.item.Ingredient;
import ru.vsu.amm.inshaker.services.items.ItemService;

import javax.validation.Valid;

@RestController
@RequestMapping("/ingredients")
public class IngredientController extends ItemController<Ingredient> {

    public IngredientController(ItemService<Ingredient> itemService) {
        super(itemService);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> one(@PathVariable Long id) {
        return super.one(id);
    }

    @Override
    @PostMapping
    public ResponseEntity<Ingredient> add(@RequestBody @Valid Ingredient item) {
        return super.add(item);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> update(@PathVariable Long id, @RequestBody @Valid Ingredient item) {
        return super.update(id, item);
    }

}
