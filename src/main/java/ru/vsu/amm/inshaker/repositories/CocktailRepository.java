package ru.vsu.amm.inshaker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.vsu.amm.inshaker.model.Cocktail;
import ru.vsu.amm.inshaker.model.user.User;

import java.util.List;
import java.util.Set;

public interface CocktailRepository extends JpaRepository<Cocktail, Long> {

    List<Cocktail> findAllByAuthorIsNull();

    @Query("select c from Cocktail c join c.taste t " +
            "where (:author is null and c.author is null or c.author = :author) " +
            "and (:spiritLow is null or :spiritHigh is null or c.spirit between :spiritLow and :spiritHigh) " +
            "and (:base is null or c.base.subgroup = :group) " +
            "and (:group is null or c.cocktailGroup = :group) " +
            "and (:search is null " +
            "or lower(c.nameEn) like lower(concat('%', :search, '%')) " +
            "or lower(c.nameRu) like lower(concat('%', :search, '%'))" +
            "or lower(c.subgroup) like lower(concat('%', :search, '%'))) " +
            "group by c having count(t) >= :tastesCount")
    List<Cocktail> findAllWithFilters(@Param("author") User author,
                                      @Param("search") String search,
                                      @Param("base") String base,
                                      @Param("spiritLow") Byte spiritLow,
                                      @Param("spiritHigh") Byte spiritHigh,
                                      @Param("group") String group,
                                      @Param("tastes") List<String> tastes,
                                      @Param("tastesCount") Long tastesCount);

    @Query("select c from Cocktail c " +
            "where (:author is null and c.author is null or c.author = :author) " +
            "and (:spiritLow is null or :spiritHigh is null or c.spirit between :spiritLow and :spiritHigh) " +
            "and (:base is null or c.base.subgroup = :group) " +
            "and (:group is null or c.cocktailGroup = :group) " +
            "and (:search is null " +
            "or lower(c.nameEn) like lower(concat('%', :search, '%')) " +
            "or lower(c.nameRu) like lower(concat('%', :search, '%'))" +
            "or lower(c.subgroup) like lower(concat('%', :search, '%')))")
    List<Cocktail> findAllWithFilters(@Param("author") User author,
                                      @Param("search") String search,
                                      @Param("base") String base,
                                      @Param("spiritLow") Byte spiritLow,
                                      @Param("spiritHigh") Byte spiritHigh,
                                      @Param("group") String group);

    @Query("select distinct c.base.subgroup from Cocktail c")
    Set<String> findDistinctBases();

    @Query("select distinct c.cocktailGroup from Cocktail c")
    Set<String> findDistinctCocktailGroups();

    @Query("select distinct t from Cocktail c inner join c.taste t")
    Set<String> findDistinctTastes();

}
