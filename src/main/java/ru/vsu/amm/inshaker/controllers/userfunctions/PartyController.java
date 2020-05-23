package ru.vsu.amm.inshaker.controllers.userfunctions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.amm.inshaker.dto.entire.PartyDTO;
import ru.vsu.amm.inshaker.dto.simple.PartySimpleDTO;
import ru.vsu.amm.inshaker.services.userfunctions.PartyService;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RolesAllowed("ROLE_USER")
@RequestMapping("/party")
public class PartyController {

    private final PartyService partyService;

    public PartyController(PartyService partyService) {
        this.partyService = partyService;
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
        PartyDTO newParty = partyService.add(party);
        if (newParty != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(newParty);
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<PartyDTO> update(@RequestBody @Valid PartyDTO party, @PathVariable Long id) {
        PartyDTO newParty = partyService.update(party, id);
        if (newParty != null) {
            return ResponseEntity.ok(newParty);
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        partyService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/invite")
    public ResponseEntity<String> invite(@PathVariable Long id, @RequestBody Long memberId) {
        partyService.invite(id, memberId);
        return ResponseEntity.ok("User " + memberId + " has access to the party " + id);
    }

    @PutMapping("/{id}/dismiss")
    public ResponseEntity<String> dismiss(@PathVariable Long id, @RequestBody Long memberId) {
        partyService.dismiss(id, memberId);
        return ResponseEntity.ok("User " + memberId + " has lost access to the party " + id);
    }

    @PutMapping("/{id}/leave")
    public ResponseEntity<String> leave(@PathVariable Long id) {
        partyService.leave(id);
        return ResponseEntity.ok("Current user leave the party " + id);
    }

}
