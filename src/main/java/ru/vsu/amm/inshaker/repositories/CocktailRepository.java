package ru.vsu.amm.inshaker.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.amm.inshaker.model.cocktail.Cocktail;
import ru.vsu.amm.inshaker.model.item.Garnish;
import ru.vsu.amm.inshaker.model.item.Ingredient;
import ru.vsu.amm.inshaker.model.item.Tableware;
import ru.vsu.amm.inshaker.model.user.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CocktailRepository extends JpaRepository<Cocktail, Long> {

    Optional<Cocktail> findByIdAndAuthorIsNull(Long id);

    Optional<Cocktail> findByIdAndAuthor(Long id, User author);

    boolean existsByIdAndAuthor(Long id, User author);

    List<Cocktail> findAllByAuthorIsNull();

    List<Cocktail> findAllByAuthor(User author);

    List<Cocktail> findAllByGlass(Tableware glass, Pageable pageable);

    List<Cocktail> findAllByGarnish(Garnish garnish, Pageable pageable);

    @Query("select distinct c from Cocktail c join c.mixingMethod m join m.tableware t where t = :tool")
    List<Cocktail> findAllByTool(@Param("tool") Tableware tool, Pageable pageable);

    @Query("select c from Cocktail c join c.recipePart r " +
            "where (c.author is null) and r.ingredient in (:bar) " +
            "group by c having count(r) + (case when :tolerance is null then 0 else :tolerance end) >= r.size")
    List<Cocktail> canBeMadeFrom(@Param("bar") Set<Ingredient> bar,
                                 @Param("tolerance") Long tolerance);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM PARTY_COCKTAIL_AMOUNT WHERE COCKTAIL_ID = :id", nativeQuery = true)
    void deletePartyCocktailAmountByCocktailId(@Param("id") Long id);

}
