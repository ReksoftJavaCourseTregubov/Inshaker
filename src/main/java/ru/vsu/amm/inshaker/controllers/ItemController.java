package ru.vsu.amm.inshaker.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.amm.inshaker.dto.properties.IngredientPropertiesDTO;
import ru.vsu.amm.inshaker.dto.simple.ItemDTO;
import ru.vsu.amm.inshaker.dto.simple.ItemGroupedDTO;
import ru.vsu.amm.inshaker.model.item.Item;
import ru.vsu.amm.inshaker.services.ItemService;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService<Item> itemService;

    public ItemController(ItemService<Item> itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<List<ItemGroupedDTO>> all() {
        return ResponseEntity.ok(itemService.getAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemDTO>> search(@RequestParam(required = false) String keyword,
                                                @RequestParam(required = false) Long category,
                                                @RequestParam(required = false) Long group,
                                                @RequestParam(required = false) Long subgroup,
                                                @RequestParam(required = false) Long base,
                                                @RequestParam(required = false) Long country,
                                                @RequestParam(required = false) Long spirit,
                                                @RequestParam(required = false) List<Long> tastes) {
        return ResponseEntity.ok(itemService.getAll(keyword, category, group, subgroup, base, country, spirit, tastes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> one(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.getOne(id));
    }

    @GetMapping("/properties")
    public ResponseEntity<IngredientPropertiesDTO> properties() {
        return ResponseEntity.ok(itemService.getProperties());
    }

    @RolesAllowed("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<Item> add(@RequestBody Item item) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.add(item));
    }

    @RolesAllowed("ROLE_ADMIN")
    @PutMapping("/{id}")
    public ResponseEntity<Item> update(@PathVariable Long id, @RequestBody @Valid Item item) {
        return ResponseEntity.ok(itemService.update(id, item));
    }

    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        itemService.delete(id);
        return ResponseEntity.ok().build();
    }

}
