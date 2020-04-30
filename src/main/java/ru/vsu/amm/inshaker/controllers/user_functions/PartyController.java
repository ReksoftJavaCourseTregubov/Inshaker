package ru.vsu.amm.inshaker.controllers.user_functions;

import org.springframework.web.bind.annotation.*;
import ru.vsu.amm.inshaker.model.dto.PartyDTO;
import ru.vsu.amm.inshaker.model.dto.PartySimpleDTO;
import ru.vsu.amm.inshaker.services.user_functions.PartyService;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RolesAllowed("ROLE_USER")
@RequestMapping("/user/party")
public class PartyController {

    private final PartyService service;

    public PartyController(PartyService service) {
        this.service = service;
    }

    @GetMapping
    public List<PartySimpleDTO> allParties() {
        return service.allParties();
    }

    @GetMapping("/{id}")
    public PartyDTO party(@PathVariable Long id) {
        return service.oneParty(id);
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
