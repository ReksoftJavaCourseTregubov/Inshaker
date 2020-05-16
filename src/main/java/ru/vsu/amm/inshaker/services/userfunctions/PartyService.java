package ru.vsu.amm.inshaker.services.userfunctions;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.dto.converters.PartyMapper;
import ru.vsu.amm.inshaker.dto.entire.PartyDTO;
import ru.vsu.amm.inshaker.dto.simple.PartySimpleDTO;
import ru.vsu.amm.inshaker.exceptions.PartyAccessDeniedException;
import ru.vsu.amm.inshaker.exceptions.notfound.PartyNotFoundException;
import ru.vsu.amm.inshaker.exceptions.notfound.UserNotFoundException;
import ru.vsu.amm.inshaker.model.Party;
import ru.vsu.amm.inshaker.model.user.User;
import ru.vsu.amm.inshaker.repositories.PartyRepository;
import ru.vsu.amm.inshaker.repositories.user.UserRepository;
import ru.vsu.amm.inshaker.services.user.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PartyService {

    private final PartyRepository partyRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final PartyMapper mapper;

    public PartyService(PartyRepository partyRepository,
                        UserRepository userRepository,
                        UserService userService,
                        PartyMapper mapper) {
        this.partyRepository = partyRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.mapper = mapper;
    }


    public Party getParty(Long id) {
        return partyRepository.findById(id)
                .orElseThrow(() -> new PartyNotFoundException(id));
    }

    public PartyDTO oneParty(Long id) {
        Party party = getParty(id);
        User currentUser = userService.getCurrentUser();
        if (party.getAuthor() == null || party.getAuthor() == currentUser || party.getMembers().contains(currentUser)) {
            return mapper.map(party);
        } else throw new PartyAccessDeniedException("User " + currentUser.getId() + " does not have permission to update party " + id);
    }

    public List<PartySimpleDTO> allParties() {
        User currentUser = userService.getCurrentUser();
        return partyRepository.findAllByAuthorIsNullOrAuthorOrMembersContains(currentUser, currentUser)
                .stream()
                .map(mapper::mapSimple)
                .collect(Collectors.toList());
    }


    public void invite(Long partyId, Long memberId) {
        Party party = getParty(partyId);
        if (party.getAuthor() == userService.getCurrentUser()) {
            party.getMembers().add(userRepository.findById(memberId)
                    .orElseThrow(() -> new UserNotFoundException(memberId)));
            partyRepository.save(party);
        } else throw new PartyAccessDeniedException("User does not have permission to update basic party");
    }

    public void dismiss(Long partyId, Long memberId) {
        Party party = getParty(partyId);
        if (party.getAuthor() == userService.getCurrentUser()) {
            party.getMembers().remove(userRepository.findById(memberId)
                    .orElseThrow(() -> new UserNotFoundException(memberId)));
            partyRepository.save(party);
        } else throw new PartyAccessDeniedException("User does not have permission to update basic party");
    }

    public void leave(Long partyId) {
        Party party = getParty(partyId);
        User currentUser = userService.getCurrentUser();
        if (party.getMembers().contains(currentUser)) {
            party.getMembers().remove(userService.getCurrentUser());
            partyRepository.save(party);
        } else throw new PartyAccessDeniedException("User " + currentUser.getId() + " does not have permission to update party " + partyId);
    }


    public PartyDTO addParty(PartyDTO party, User author) {
        Party newParty = mapper.map(party);
        newParty.setId(null);
        newParty.setAuthor(author);
        return mapper.map(partyRepository.save(newParty));
    }

    public PartyDTO updateParty(PartyDTO newParty, Long id, User author) {
        return partyRepository.findByIdAndAuthor(id, author)
                .map(oldParty -> {
                    BeanUtils.copyProperties(mapper.map(newParty), oldParty);
                    oldParty.setId(id);
                    oldParty.setAuthor(author);
                    return mapper.map(partyRepository.save(oldParty));
                }).orElseThrow(() -> new PartyNotFoundException(id));
    }

    public void deleteParty(Long id, User author) {
        if (partyRepository.existsByIdAndAuthor(id, author)) {
            partyRepository.deleteById(id);
        } else throw new PartyNotFoundException(id);
    }


    public PartyDTO add(PartyDTO party) {
        User currentUser = userService.getCurrentUser();
        return addParty(party, (userService.userHasRole(currentUser, "ROLE_ADMIN") ? null : currentUser));
    }

    public PartyDTO update(PartyDTO party, Long id) {
        User currentUser = userService.getCurrentUser();
        return updateParty(party, id, (userService.userHasRole(currentUser, "ROLE_ADMIN") ? null : currentUser));
    }

    public void delete(Long id) {
        User currentUser = userService.getCurrentUser();
        deleteParty(id, (userService.userHasRole(currentUser, "ROLE_ADMIN") ? null : currentUser));
    }

}
