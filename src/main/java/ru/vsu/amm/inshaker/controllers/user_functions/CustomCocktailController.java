package ru.vsu.amm.inshaker.controllers.user_functions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.amm.inshaker.dto.entire.CocktailDTO;
import ru.vsu.amm.inshaker.dto.simple.CocktailSimpleDTO;
import ru.vsu.amm.inshaker.services.user_functions.CustomCocktailService;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RolesAllowed("ROLE_USER")
@RequestMapping("/user/cocktails")
public class CustomCocktailController {

    private final CustomCocktailService service;

    public CustomCocktailController(CustomCocktailService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CocktailSimpleDTO>> all() {
        return ResponseEntity.ok(service.getAllCustom());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CocktailDTO> one(@PathVariable Long id) {
        return ResponseEntity.ok(service.getOneCustom(id));
    }

    @PostMapping
    public ResponseEntity<CocktailDTO> add(@RequestBody @Valid CocktailDTO cocktail) {
        CocktailDTO newCocktail = service.addCustom(cocktail);
        if (newCocktail != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(newCocktail);
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CocktailDTO> update(@RequestBody @Valid CocktailDTO cocktail, @PathVariable Long id) {
        CocktailDTO newCocktail = service.updateCustom(cocktail, id);
        if (newCocktail != null) {
            return ResponseEntity.ok(newCocktail);
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteCustom(id);
        return ResponseEntity.ok().build();
    }

}
