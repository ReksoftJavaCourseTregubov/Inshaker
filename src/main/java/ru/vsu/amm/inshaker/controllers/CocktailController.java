package ru.vsu.amm.inshaker.controllers;

import org.springframework.web.bind.annotation.*;
import ru.vsu.amm.inshaker.dto.entire.CocktailDTO;
import ru.vsu.amm.inshaker.dto.properties.CocktailPropertiesDTO;
import ru.vsu.amm.inshaker.dto.simple.CocktailSimpleDTO;
import ru.vsu.amm.inshaker.services.CocktailService;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/cocktails")
public class CocktailController {

    private final CocktailService service;

    public CocktailController(CocktailService service) {
        this.service = service;
    }

    @GetMapping
    public List<CocktailSimpleDTO> all() {
        return service.getAll();
    }

    @GetMapping("/search")
    public List<CocktailSimpleDTO> search(@RequestParam(required = false) String keyword,
                                          @RequestParam(required = false) Long base,
                                          @RequestParam(required = false) Long group,
                                          @RequestParam(required = false) Long subgroup,
                                          @RequestParam(required = false) Long spirit,
                                          @RequestParam(required = false) Long mixingMethod,
                                          @RequestParam(required = false) List<Long> tastes) {
        return service.getAll(keyword, base, group, subgroup, spirit, mixingMethod, tastes);
    }

    @GetMapping("/{id}")
    public CocktailDTO one(@PathVariable Long id) {
        return service.getOne(id);
    }

    @GetMapping("/popular")
    public List<CocktailSimpleDTO> popular(@RequestParam(required = false) @Positive Integer limit) {
        return service.getPopular(limit == null ? 16 : limit);
    }

    @GetMapping("/properties")
    public CocktailPropertiesDTO properties() {
        return service.getProperties();
    }

    @RolesAllowed("ROLE_ADMIN")
    @PostMapping
    public CocktailDTO add(@RequestBody @Valid CocktailDTO cocktail) {
        return service.add(cocktail);
    }

    @RolesAllowed("ROLE_ADMIN")
    @PutMapping("/{id}")
    CocktailDTO update(@PathVariable Long id, @RequestBody @Valid CocktailDTO cocktail) {
        return service.update(cocktail, id);
    }

    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
