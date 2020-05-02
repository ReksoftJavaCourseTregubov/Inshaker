package ru.vsu.amm.inshaker.services.user_functions;

import org.springframework.beans.BeanUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.exceptions.entity_not_foound_exceptions.PartyNotFoundException;
import ru.vsu.amm.inshaker.exceptions.entity_not_foound_exceptions.UserNotFoundException;
import ru.vsu.amm.inshaker.model.Party;
import ru.vsu.amm.inshaker.model.dto.converters.PartyDTOConverter;
import ru.vsu.amm.inshaker.model.dto.entire.PartyDTO;
import ru.vsu.amm.inshaker.model.dto.simple.PartySimpleDTO;
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
    private final PartyDTOConverter converter;

    public PartyService(PartyRepository partyRepository,
                        UserRepository userRepository,
                        UserService userService,
                        PartyDTOConverter converter) {
        this.partyRepository = partyRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.converter = converter;
    }


    public Party getParty(Long id) {
        return partyRepository.findById(id)
                .orElseThrow(() -> new PartyNotFoundException(id));
    }

    public List<Party> getAllParties() {
        User currentUser = userService.getCurrentUser();
        return partyRepository.findAllByAuthorIsNullOrAuthorOrMembersContains(currentUser, currentUser);
    }


    public PartyDTO oneParty(Long id) {
        Party party = getParty(id);
        User currentUser = userService.getCurrentUser();
        if (party.getAuthor() == null || party.getAuthor() == currentUser || party.getMembers().contains(currentUser)) {
            return converter.convert(party);
        } else throw new AccessDeniedException("The user does not have permission to get the party " + id);
    }

    public List<PartySimpleDTO> allParties() {
        return getAllParties().stream()
                .map(converter::convertSimple).collect(Collectors.toList());
    }


    public void invite(Long partyId, Long memberId) {
        Party party = getParty(partyId);
        if (party.getAuthor() == userService.getCurrentUser()) {
            party.getMembers().add(userRepository.findById(memberId)
                    .orElseThrow(() -> new UserNotFoundException(memberId)));
            partyRepository.save(party);
        } else throw new AccessDeniedException("User does not have permission to update basic party");
    }

    public void dismiss(Long partyId, Long memberId) {
        Party party = getParty(partyId);
        if (party.getAuthor() == userService.getCurrentUser()) {
            party.getMembers().remove(userRepository.findById(memberId)
                    .orElseThrow(() -> new UserNotFoundException(memberId)));
            partyRepository.save(party);
        } else throw new AccessDeniedException("User does not have permission to update basic party");
    }

    public void leave(Long partyId) {
        Party party = getParty(partyId);
        User currentUser = userService.getCurrentUser();
        if (party.getMembers().contains(currentUser)) {
            party.getMembers().remove(userService.getCurrentUser());
            partyRepository.save(party);
        } else throw new AccessDeniedException("User does not have permission to update party");
    }


    public PartyDTO addParty(PartyDTO party, User author) {
        Party newParty = converter.convert(party);
        newParty.setId(null);
        newParty.setAuthor(author);
        return converter.convert(partyRepository.save(newParty));
    }

    public PartyDTO updateParty(PartyDTO newParty, Long id, User author) {
        return partyRepository.findByIdAndAuthor(id, author)
                .map(oldParty -> {
                    BeanUtils.copyProperties(converter.convert(newParty), oldParty);
                    oldParty.setId(id);
                    oldParty.setAuthor(author);
                    return converter.convert(partyRepository.save(oldParty));
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
