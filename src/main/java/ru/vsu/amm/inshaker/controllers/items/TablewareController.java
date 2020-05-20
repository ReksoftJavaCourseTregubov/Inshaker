package ru.vsu.amm.inshaker.controllers.items;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.amm.inshaker.dto.entire.items.TablewareDTO;
import ru.vsu.amm.inshaker.model.item.Tableware;
import ru.vsu.amm.inshaker.services.items.TablewareService;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("/tableware")
public class TablewareController extends ItemController<Tableware, TablewareDTO> {

    public TablewareController(TablewareService tablewareService) {
        super(tablewareService);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<TablewareDTO> one(@PathVariable Long id) {
        return super.one(id);
    }

    @Override
    @RolesAllowed("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<TablewareDTO> add(@RequestBody @Valid TablewareDTO tableware) {
        return super.add(tableware);
    }

    @Override
    @RolesAllowed("ROLE_ADMIN")
    @PutMapping("/{id}")
    public ResponseEntity<TablewareDTO> update(@PathVariable Long id, @RequestBody @Valid TablewareDTO tableware) {
        return super.update(id, tableware);
    }

}
