package ru.vsu.amm.inshaker.repositories.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.vsu.amm.inshaker.model.cocktail.Cocktail;
import ru.vsu.amm.inshaker.model.user.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    @Query("select u from User u join u.roles r where r.name = :role")
    List<User> findAllByRole(@Param("role") String role);

    @Query("select c from User u join u.favorite c where c.author is null group by c order by count(c) desc")
    List<Cocktail> findPopularCocktails(Pageable pageable);

}
