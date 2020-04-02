package ru.vsu.amm.inshaker.controllers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.amm.inshaker.model.Cocktail;
import ru.vsu.amm.inshaker.services.CocktailModelAssembler;
import ru.vsu.amm.inshaker.services.CocktailService;

@RestController
public class CocktailController {

    private final CocktailService service;
    private final CocktailModelAssembler assembler;

    public CocktailController(CocktailModelAssembler assembler, CocktailService service) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping("/cocktails")
    public CollectionModel<EntityModel<Cocktail>> all() {
        return assembler.toCollectionModel(service.getAll());
    }

    @GetMapping("/cocktails/{id}")
    public EntityModel<Cocktail> one(@PathVariable Long id) {
        return assembler.toModel(service.get(id));
    }

}
