package ru.vsu.amm.inshaker.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.amm.inshaker.model.user.Role;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Set<Role> findAllByNameIn(Set<String> name);

}
