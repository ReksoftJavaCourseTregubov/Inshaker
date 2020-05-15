package ru.vsu.amm.inshaker.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private static final int PAGE_REQUEST_DEFAULT_LIMIT = 16;

    private final CocktailService service;

    public CocktailController(CocktailService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CocktailSimpleDTO>> all() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<CocktailSimpleDTO>> search(@RequestParam(required = false) String keyword,
                                                          @RequestParam(required = false) Long base,
                                                          @RequestParam(required = false) Long group,
                                                          @RequestParam(required = false) Long subgroup,
                                                          @RequestParam(required = false) Long spirit,
                                                          @RequestParam(required = false) Long mixingMethod,
                                                          @RequestParam(required = false) List<Long> tastes) {
        return ResponseEntity.ok(service.getAll(keyword, base, group, subgroup, spirit, mixingMethod, tastes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CocktailDTO> one(@PathVariable Long id) {
        return ResponseEntity.ok(service.getOne(id));
    }

    @GetMapping("/popular")
    public ResponseEntity<List<CocktailSimpleDTO>> popular(@RequestParam(required = false) @Positive Integer limit) {
        return ResponseEntity.ok(service.getPopular(limit == null ? PAGE_REQUEST_DEFAULT_LIMIT : limit));
    }

    @GetMapping("/properties")
    public ResponseEntity<CocktailPropertiesDTO> properties() {
        return ResponseEntity.ok(service.getProperties());
    }

    @RolesAllowed("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<CocktailDTO> add(@RequestBody @Valid CocktailDTO cocktail) {
        CocktailDTO newCocktail = service.add(cocktail);
        if (newCocktail != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(newCocktail);
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @RolesAllowed("ROLE_ADMIN")
    @PutMapping("/{id}")
    public ResponseEntity<CocktailDTO> update(@PathVariable Long id, @RequestBody @Valid CocktailDTO cocktail) {
        CocktailDTO newCocktail = service.update(cocktail, id);
        if (newCocktail != null) {
            return ResponseEntity.ok(newCocktail);
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
