package ru.vsu.amm.inshaker.controllers.items;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.amm.inshaker.model.item.Garnish;
import ru.vsu.amm.inshaker.services.items.ItemService;

import javax.validation.Valid;

@RestController
@RequestMapping("/garnish")
public class GarnishController extends ItemController<Garnish> {

    public GarnishController(ItemService<Garnish> itemService) {
        super(itemService);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Garnish> one(@PathVariable Long id) {
        return super.one(id);
    }

    @Override
    @PostMapping
    public ResponseEntity<Garnish> add(@RequestBody @Valid Garnish item) {
        return super.add(item);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Garnish> update(@PathVariable Long id, @RequestBody @Valid Garnish item) {
        return super.update(id, item);
    }

}
