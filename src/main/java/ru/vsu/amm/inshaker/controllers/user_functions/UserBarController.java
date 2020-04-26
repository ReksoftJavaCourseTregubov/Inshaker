package ru.vsu.amm.inshaker.controllers.user_functions;

import org.springframework.web.bind.annotation.*;
import ru.vsu.amm.inshaker.model.dto.CocktailSimpleDTO;
import ru.vsu.amm.inshaker.model.dto.IngredientSimpleDTO;
import ru.vsu.amm.inshaker.services.user_functions.BarService;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Set;

@RestController
@RolesAllowed("ROLE_USER")
@RequestMapping("/user/bar")
public class UserBarController {

    private final BarService service;

    public UserBarController(BarService service) {
        this.service = service;
    }

    @GetMapping("/ingredients")
    public Set<IngredientSimpleDTO> bar() {
        return service.getBar();
    }

    @PostMapping("/ingredients")
    public void addToBar(@RequestBody Long id) {
        service.addToBar(id);
    }

    @DeleteMapping("/ingredients/{id}")
    public void deleteFromBar(@PathVariable("id") Long id) {
        service.deleteFromBar(id);
    }

    @GetMapping("/cocktails")
    public List<CocktailSimpleDTO> barCocktails(@RequestParam(required = false) @PositiveOrZero Long tolerance) {
        return service.getAvailableCocktails(tolerance);
    }

}
