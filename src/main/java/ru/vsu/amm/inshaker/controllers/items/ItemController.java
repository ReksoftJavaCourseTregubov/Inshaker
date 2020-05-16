package ru.vsu.amm.inshaker.controllers.items;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.amm.inshaker.dto.properties.IngredientPropertiesDTO;
import ru.vsu.amm.inshaker.dto.simple.ItemDTO;
import ru.vsu.amm.inshaker.dto.simple.ItemGroupedDTO;
import ru.vsu.amm.inshaker.model.item.Item;
import ru.vsu.amm.inshaker.services.items.ItemService;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController<T extends Item> {

    private final ItemService<T> itemService;

    public ItemController(ItemService<T> itemService) {
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
    protected ResponseEntity<T> one(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.getOne(id));
    }

    @GetMapping("/properties")
    public ResponseEntity<IngredientPropertiesDTO> properties() {
        return ResponseEntity.ok(itemService.getProperties());
    }

    protected ResponseEntity<T> add(T item) {
        T newItem = itemService.add(item);
        if (newItem != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(newItem);
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    public ResponseEntity<T> update(Long id, T item) {
        T newItem = itemService.update(id, item);
        if (newItem != null) {
            return ResponseEntity.ok(newItem);
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        itemService.delete(id);
        return ResponseEntity.ok().build();
    }

}
