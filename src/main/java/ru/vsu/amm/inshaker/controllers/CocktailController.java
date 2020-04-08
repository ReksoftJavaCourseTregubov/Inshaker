package ru.vsu.amm.inshaker.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.amm.inshaker.model.dto.CocktailDTO;
import ru.vsu.amm.inshaker.model.dto.CocktailSimpleDTO;
import ru.vsu.amm.inshaker.services.CocktailService;

import java.util.List;
import java.util.Set;

@RestController
public class CocktailController {

    private final CocktailService service;

    public CocktailController(CocktailService service) {
        this.service = service;
    }

    @GetMapping("/cocktails")
    public List<CocktailSimpleDTO> all(@RequestParam(required = false) String search,
                                       @RequestParam(required = false) String base,
                                       @RequestParam(required = false) String spirit,
                                       @RequestParam(required = false) String group,
                                       @RequestParam(required = false) List<String> tastes) {
        return service.getAll(search, base, spirit, group, tastes);
    }

    @GetMapping("/cocktails/{id}")
    public CocktailDTO one(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping("/cocktails/bases")
    public Set<String> bases() {
        return service.getBases();
    }

    @GetMapping("/cocktails/groups")
    public Set<String> groups() {
        return service.getCocktailsGroups();
    }

    @GetMapping("/cocktails/tastes")
    public Set<String> tastes() {
        return service.getTastes();
    }

    @GetMapping("/cocktails/spirits")
    public Set<String> spirits() {
        return service.getSpirits();
    }

}
