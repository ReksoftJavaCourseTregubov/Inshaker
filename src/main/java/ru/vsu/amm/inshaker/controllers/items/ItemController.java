package ru.vsu.amm.inshaker.controllers.items;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.amm.inshaker.dto.entire.ItemGroupedDTO;
import ru.vsu.amm.inshaker.dto.entire.items.ItemDTO;
import ru.vsu.amm.inshaker.dto.properties.IngredientPropertiesDTO;
import ru.vsu.amm.inshaker.model.item.Item;
import ru.vsu.amm.inshaker.services.items.ItemService;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController<T extends Item, S extends ItemDTO> {

    private final ItemService<T, S> itemService;

    public ItemController(ItemService<T, S> itemService) {
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

    @GetMapping("/properties")
    public ResponseEntity<IngredientPropertiesDTO> properties() {
        return ResponseEntity.ok(itemService.getProperties());
    }

    protected ResponseEntity<S> one(Long id) {
        return ResponseEntity.ok(itemService.getOne(id));
    }

    protected ResponseEntity<S> add(S item) {
        S newItem = itemService.add(item);
        if (newItem != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(newItem);
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    public ResponseEntity<S> update(Long id, S item) {
        S newItem = itemService.update(id, item);
        if (newItem != null) {
            return ResponseEntity.ok(newItem);
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        itemService.delete(id);
        return ResponseEntity.ok().build();
    }

}
