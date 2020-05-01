package ru.vsu.amm.inshaker.controllers;

import org.springframework.web.bind.annotation.*;
import ru.vsu.amm.inshaker.model.dto.entire.IngredientDTO;
import ru.vsu.amm.inshaker.model.dto.simple.IngredientSimpleDTO;
import ru.vsu.amm.inshaker.services.IngredientService;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientService service;

    public IngredientController(IngredientService service) {
        this.service = service;
    }

    @GetMapping
    public List<IngredientSimpleDTO> all(@RequestParam(required = false) String search,
                                         @RequestParam(required = false) String spirit,
                                         @RequestParam(required = false) String group,
                                         @RequestParam(required = false) List<String> tastes) {
        return service.getAll(search, spirit, group, tastes);
    }

    @RolesAllowed("ROLE_ADMIN")
    @PostMapping
    public IngredientDTO add(@RequestBody @Valid IngredientDTO ingredient) {
        return service.add(ingredient);
    }

    @GetMapping("/{id}")
    public IngredientDTO one(@PathVariable Long id) {
        return service.get(id);
    }

    @RolesAllowed("ROLE_ADMIN")
    @PutMapping("/{id}")
    IngredientDTO update(@RequestBody @Valid IngredientDTO ingredient, @PathVariable Long id) {
        return service.update(ingredient, id);
    }

    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/groups")
    public Set<String> groups() {
        return service.getIngredientsGroups();
    }

    @GetMapping("/tastes")
    public Set<String> tastes() {
        return service.getTastes();
    }

    @GetMapping("/spirits")
    public Set<String> spirits() {
        return service.getSpirits();
    }

}
