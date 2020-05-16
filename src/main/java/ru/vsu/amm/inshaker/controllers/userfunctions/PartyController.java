package ru.vsu.amm.inshaker.controllers.userfunctions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.amm.inshaker.dto.entire.PartyDTO;
import ru.vsu.amm.inshaker.dto.simple.UserSimpleDTO;
import ru.vsu.amm.inshaker.dto.simple.PartySimpleDTO;
import ru.vsu.amm.inshaker.services.user.UserService;
import ru.vsu.amm.inshaker.services.userfunctions.PartyService;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RolesAllowed("ROLE_USER")
@RequestMapping("/party")
public class PartyController {

    private final PartyService partyService;
    private final UserService userService;

    public PartyController(PartyService partyService, UserService userService) {
        this.partyService = partyService;
        this.userService = userService;
    }

    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping
    public ResponseEntity<List<PartySimpleDTO>> all() {
        return ResponseEntity.ok(partyService.allParties());
    }

    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/{id}")
    public ResponseEntity<PartyDTO> one(@PathVariable Long id) {
        return ResponseEntity.ok(partyService.oneParty(id));
    }

    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<PartyDTO> add(@RequestBody @Valid PartyDTO party) {
        return ResponseEntity.ok(partyService.add(party));
    }

    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @PutMapping("/{id}")
    PartyDTO update(@RequestBody @Valid PartyDTO cocktail, @PathVariable Long id) {
        return partyService.update(cocktail, id);
    }

    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        partyService.delete(id);
    }

    @PutMapping("/{id}/invite")
    public void invite(@PathVariable Long id, @RequestBody Long memberId) {
        partyService.invite(id, memberId);
    }

    @PutMapping("/{id}/dismiss")
    public void dismiss(@PathVariable Long id, @RequestBody Long memberId) {
        partyService.dismiss(id, memberId);
    }

    @PutMapping("/{id}/leave")
    public void leave(@PathVariable Long id) {
        partyService.leave(id);
    }

    @GetMapping("/users")
    public List<UserSimpleDTO> users() {
        return userService.getAllUsers();
    }

}
