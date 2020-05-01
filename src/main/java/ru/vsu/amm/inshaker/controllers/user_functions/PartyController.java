package ru.vsu.amm.inshaker.controllers.user_functions;

import org.springframework.web.bind.annotation.*;
import ru.vsu.amm.inshaker.model.dto.entire.PartyDTO;
import ru.vsu.amm.inshaker.model.dto.simple.PartySimpleDTO;
import ru.vsu.amm.inshaker.services.user_functions.PartyService;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RolesAllowed("ROLE_USER")
@RequestMapping("/user/party")
public class PartyController {

    private final PartyService service;

    public PartyController(PartyService service) {
        this.service = service;
    }

    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping
    public List<PartySimpleDTO> all() {
        return service.allParties();
    }

    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/{id}")
    public PartyDTO one(@PathVariable Long id) {
        return service.oneParty(id);
    }

    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping
    public PartyDTO add(@RequestBody @Valid PartyDTO party) {
        return service.add(party);
    }

    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @PutMapping("/{id}")
    PartyDTO update(@RequestBody @Valid PartyDTO cocktail, @PathVariable Long id) {
        return service.update(cocktail, id);
    }

    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PutMapping("/{id}/invite")
    public void invite(@PathVariable Long id, @RequestBody Long memberId) {
        service.invite(id, memberId);
    }

    @PutMapping("/{id}/dismiss")
    public void dismiss(@PathVariable Long id, @RequestBody Long memberId) {
        service.dismiss(id, memberId);
    }

    @PutMapping("/{id}/leave")
    public void leave(@PathVariable Long id) {
        service.leave(id);
    }

}
