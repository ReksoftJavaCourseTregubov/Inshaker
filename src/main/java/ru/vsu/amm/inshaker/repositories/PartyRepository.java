package ru.vsu.amm.inshaker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.amm.inshaker.model.Party;
import ru.vsu.amm.inshaker.model.user.User;

import java.util.List;
import java.util.Optional;

public interface PartyRepository extends JpaRepository<Party, Long> {

    Optional<Party> findByIdAndAuthor(Long id, User author);

    List<Party> findAllByAuthorIsNullOrAuthorOrMembersContains(User author, User member);

    boolean existsByIdAndAuthor(Long id, User author);

}
