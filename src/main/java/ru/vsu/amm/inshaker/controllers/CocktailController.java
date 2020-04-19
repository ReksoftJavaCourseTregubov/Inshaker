package ru.vsu.amm.inshaker.controllers;

import org.springframework.web.bind.annotation.*;
import ru.vsu.amm.inshaker.model.dto.CocktailDTO;
import ru.vsu.amm.inshaker.model.dto.CocktailSimpleDTO;
import ru.vsu.amm.inshaker.services.CocktailService;

import javax.annotation.security.RolesAllowed;
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

    @RolesAllowed("ROLE_ADMIN")
    @PostMapping("/cocktails")
    public CocktailDTO add(@RequestBody CocktailDTO cocktail) {
        return service.add(cocktail);
    }

    @GetMapping("/cocktails/{id}")
    public CocktailDTO one(@PathVariable Long id) {
        return service.get(id);
    }

    @RolesAllowed("ROLE_ADMIN")
    @PutMapping("/cocktails/{id}")
    CocktailDTO update(@RequestBody CocktailDTO cocktail, @PathVariable Long id) {
        return service.update(cocktail, id);
    }

    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/cocktails/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
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
