package ru.vsu.amm.inshaker.controllers.items;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.amm.inshaker.dto.entire.items.GarnishDTO;
import ru.vsu.amm.inshaker.model.item.Garnish;
import ru.vsu.amm.inshaker.services.items.GarnishService;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("/garnish")
public class GarnishController extends IngredientController<Garnish, GarnishDTO> {

    public GarnishController(GarnishService garnishService) {
        super(garnishService);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<GarnishDTO> one(@PathVariable Long id) {
        return super.one(id);
    }

    @Override
    @RolesAllowed("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<GarnishDTO> add(@RequestBody @Valid GarnishDTO garnish) {
        return super.add(garnish);
    }

    @Override
    @RolesAllowed("ROLE_ADMIN")
    @PutMapping("/{id}")
    public ResponseEntity<GarnishDTO> update(@PathVariable Long id, @RequestBody @Valid GarnishDTO garnish) {
        return super.update(id, garnish);
    }

}
