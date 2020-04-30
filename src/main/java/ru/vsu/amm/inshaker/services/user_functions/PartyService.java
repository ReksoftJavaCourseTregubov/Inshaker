package ru.vsu.amm.inshaker.services.user_functions;

import org.springframework.beans.BeanUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.exceptions.PartyNotFoundException;
import ru.vsu.amm.inshaker.exceptions.UserNotFoundException;
import ru.vsu.amm.inshaker.model.Party;
import ru.vsu.amm.inshaker.model.dto.PartyDTO;
import ru.vsu.amm.inshaker.model.dto.PartySimpleDTO;
import ru.vsu.amm.inshaker.model.dto.converters.PartyDTOConverter;
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
            return converter.convertTo(party);
        } else throw new AccessDeniedException("The user does not have permission to get the party " + id);
    }

    public List<PartySimpleDTO> allParties() {
        return getAllParties().stream()
                .map(converter::convertToSimple).collect(Collectors.toList());
    }


    public void invite(Long partyId, Long memberId) {
        Party party = getParty(partyId);
        if (party.getAuthor() == userService.getCurrentUser()) {
            party.getMembers().add(userRepository.findById(memberId)
                    .orElseThrow(() -> new UserNotFoundException(memberId)));
            partyRepository.save(party);

        }
    }

    public void dismiss(Long partyId, Long memberId) {
        Party party = getParty(partyId);
        if (party.getAuthor() == userService.getCurrentUser()) {
            party.getMembers().remove(userRepository.findById(memberId)
                    .orElseThrow(() -> new UserNotFoundException(memberId)));
            partyRepository.save(party);
        }
    }

    public void leave(Long partyId) {
        Party party = getParty(partyId);
        party.getMembers().remove(userService.getCurrentUser());
        partyRepository.save(party);
    }


    public PartyDTO addParty(PartyDTO party, User author) {
        Party newCocktail = converter.convertFrom(party);
        newCocktail.setId(null);
        newCocktail.setAuthor(author);
        return converter.convertTo(partyRepository.save(newCocktail));
    }

    public PartyDTO updateParty(PartyDTO newParty, Long id, User author) {
        return partyRepository.findByIdAndAuthor(id, author)
                .map(oldParty -> {
                    BeanUtils.copyProperties(converter.convertFrom(newParty), oldParty);
                    oldParty.setId(id);
                    return converter.convertTo(partyRepository.save(oldParty));
                }).orElseThrow(() -> new PartyNotFoundException(id));
    }

    public void deleteParty(Long id, User author) {
        if (partyRepository.existsByIdAndAuthor(id, author)) {
            partyRepository.deleteById(id);
        } else throw new PartyNotFoundException(id);
    }


    public PartyDTO add(PartyDTO party) {
        return addParty(party, null);
    }

    public PartyDTO update(PartyDTO party, Long id) {
        return updateParty(party, id, null);
    }

    public void delete(Long id) {
        deleteParty(id, null);
    }


    public PartyDTO addCustom(PartyDTO party) {
        return addParty(party, userService.getCurrentUser());
    }

    public PartyDTO updateCustom(PartyDTO party, Long id) {
        return updateParty(party, id, userService.getCurrentUser());
    }

    public void deleteCustom(Long id) {
        deleteParty(id, userService.getCurrentUser());
    }

}
