package ru.vsu.amm.inshaker.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.amm.inshaker.model.dto.IngredientDTO;
import ru.vsu.amm.inshaker.model.dto.IngredientSimpleDTO;
import ru.vsu.amm.inshaker.services.IngredientService;

import java.util.List;
import java.util.Set;

@RestController
public class IngredientController {

    private final IngredientService service;

    public IngredientController(IngredientService service) {
        this.service = service;
    }

    @GetMapping("/ingredients")
    public List<IngredientSimpleDTO> all(@RequestParam(required = false) String search,
                                         @RequestParam(required = false) String spirit,
                                         @RequestParam(required = false) String group,
                                         @RequestParam(required = false) List<String> tastes) {
        return service.getAll(search, spirit, group, tastes);
    }

    @GetMapping("/ingredients/{id}")
    public IngredientDTO one(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping("/ingredients/groups")
    public Set<String> groups() {
        return service.getIngredientsGroups();
    }

    @GetMapping("/ingredients/tastes")
    public Set<String> tastes() {
        return service.getTastes();
    }

    @GetMapping("/ingredients/spirits")
    public Set<String> spirits() {
        return service.getSpirits();
    }

}
