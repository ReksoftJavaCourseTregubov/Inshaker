package ru.vsu.amm.inshaker.controllers;

import org.springframework.web.bind.annotation.*;
import ru.vsu.amm.inshaker.model.dto.entire.CocktailDTO;
import ru.vsu.amm.inshaker.model.dto.simple.CocktailSimpleDTO;
import ru.vsu.amm.inshaker.services.CocktailService;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/cocktails")
public class CocktailController {

    private final CocktailService service;

    public CocktailController(CocktailService service) {
        this.service = service;
    }

    @GetMapping
    public List<CocktailSimpleDTO> all(@RequestParam(required = false) String search,
                                       @RequestParam(required = false) String base,
                                       @RequestParam(required = false) String spirit,
                                       @RequestParam(required = false) String group,
                                       @RequestParam(required = false) List<String> tastes) {
        return service.getAll(search, base, spirit, group, tastes);
    }

    @RolesAllowed("ROLE_ADMIN")
    @PostMapping
    public CocktailDTO add(@RequestBody @Valid CocktailDTO cocktail) {
        return service.add(cocktail);
    }

    @GetMapping("/{id}")
    public CocktailDTO one(@PathVariable Long id) {
        return service.get(id);
    }

    @RolesAllowed("ROLE_ADMIN")
    @PutMapping("/{id}")
    CocktailDTO update(@RequestBody @Valid CocktailDTO cocktail, @PathVariable Long id) {
        return service.update(cocktail, id);
    }

    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/popular")
    public List<CocktailSimpleDTO> popular(@RequestParam(required = false) @Positive Integer limit) {
        return service.getPopular(limit == null ? 16 : limit);
    }

    @GetMapping("/bases")
    public Set<String> bases() {
        return service.getBases();
    }

    @GetMapping("/groups")
    public Set<String> groups() {
        return service.getCocktailsGroups();
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
