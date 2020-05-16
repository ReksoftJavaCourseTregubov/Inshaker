package ru.vsu.amm.inshaker.controllers.items;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.amm.inshaker.model.item.Tableware;
import ru.vsu.amm.inshaker.services.items.ItemService;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("/tableware")
public class TablewareController extends ItemController<Tableware> {

    public TablewareController(ItemService<Tableware> itemService) {
        super(itemService);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Tableware> one(@PathVariable Long id) {
        return super.one(id);
    }

    @Override
    @RolesAllowed("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<Tableware> add(@RequestBody @Valid Tableware item) {
        return super.add(item);
    }

    @Override
    @RolesAllowed("ROLE_ADMIN")
    @PutMapping("/{id}")
    public ResponseEntity<Tableware> update(@PathVariable Long id, @RequestBody @Valid Tableware item) {
        return super.update(id, item);
    }

}
