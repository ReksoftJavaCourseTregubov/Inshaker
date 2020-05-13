package ru.vsu.amm.inshaker.controllers.user_functions;

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
    public List<CocktailSimpleDTO> all() {
        return service.getAllCustoms();
    }

    @PostMapping
    public CocktailDTO add(@RequestBody @Valid CocktailDTO cocktail) {
        return service.addCustom(cocktail);
    }

    @GetMapping("/{id}")
    public CocktailDTO one(@PathVariable Long id) {
        return service.getCustom(id);
    }

    @PutMapping("/{id}")
    CocktailDTO update(@RequestBody @Valid CocktailDTO cocktail, @PathVariable Long id) {
        return service.updateCustom(cocktail, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteCustom(id);
    }

}
