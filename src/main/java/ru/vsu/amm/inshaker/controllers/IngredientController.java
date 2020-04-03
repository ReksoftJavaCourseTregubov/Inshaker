package ru.vsu.amm.inshaker.controllers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.amm.inshaker.model.Ingredient;
import ru.vsu.amm.inshaker.services.IngredientModelAssembler;
import ru.vsu.amm.inshaker.services.IngredientService;

import java.util.List;

@RestController
public class IngredientController {

    private final IngredientService service;
    private final IngredientModelAssembler assembler;

    public IngredientController(IngredientModelAssembler assembler, IngredientService service) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping("/ingredients")
    public CollectionModel<EntityModel<Ingredient>> all() {
        return assembler.toCollectionModel(service.getAll());
    }

    @GetMapping("/ingredients/subset")
    public CollectionModel<EntityModel<Ingredient>> all(@RequestParam(required = false) String search,
                                                        @RequestParam(required = false) String spirit,
                                                        @RequestParam(required = false) String group,
                                                        @RequestParam(required = false) List<String> tastes) {
        return assembler.toCollectionModel(service.getAll(search, spirit, group, tastes));
    }

    @GetMapping("/ingredients/{id}")
    public EntityModel<Ingredient> one(@PathVariable Long id) {
        return assembler.toModel(service.get(id));
    }

    @GetMapping("/ingredients/groups")
    public CollectionModel<String> groups() {
        return assembler.groupsToCollectionModel(service.getIngredientsGroups());
    }

    @GetMapping("/ingredients/tastes")
    public CollectionModel<String> tastes() {
        return assembler.tastesToCollectionModel(service.getTastes());
    }

}
