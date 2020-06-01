package ru.vsu.amm.inshaker.controllers.items;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.amm.inshaker.dto.entire.items.GarnishDTO;
import ru.vsu.amm.inshaker.model.item.Garnish;
import ru.vsu.amm.inshaker.services.items.GarnishService;

@RestController
@RequestMapping("/garnish")
public class GarnishController extends IngredientController<Garnish, GarnishDTO> {

    public GarnishController(GarnishService garnishService) {
        super(garnishService);
    }

}
