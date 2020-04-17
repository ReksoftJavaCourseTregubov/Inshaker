package ru.vsu.amm.inshaker.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.amm.inshaker.model.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
