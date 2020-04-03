package ru.vsu.amm.inshaker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.vsu.amm.inshaker.model.Ingredient;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    @Query("select distinct t from Ingredient i inner join i.taste t")
    List<String> findDistinctTastes();

    @Query("select distinct i.ingredientGroup from Ingredient i")
    List<String> findDistinctIngredientGroups();

    @Query("select i from Ingredient i join i.taste t " +
            "where (:spiritLow is null or :spiritHigh is null or i.spirit between :spiritLow and :spiritHigh) " +
            "and (:group is null or i.ingredientGroup = :group) " +
            "and ((:tastes) is null or t in (:tastes)) " +
            "and (:search is null " +
            "or lower(i.nameEn) like lower(concat('%', :search, '%')) " +
            "or lower(i.nameRu) like lower(concat('%', :search, '%'))" +
            "or lower(i.subgroup) like lower(concat('%', :search, '%')))" +
            "group by i having count(t) >= :tastesCount")
    List<Ingredient> findAllWithFilters(@Param("search") String search,
                                        @Param("spiritLow") Byte spiritLow,
                                        @Param("spiritHigh") Byte spiritHigh,
                                        @Param("group") String group,
                                        @Param("tastes") List<String> tastes,
                                        @Param("tastesCount") Long tastesCount);

    @Query("select i from Ingredient i " +
            "where (:spiritLow is null or :spiritHigh is null or i.spirit between :spiritLow and :spiritHigh) " +
            "and (:group is null or i.ingredientGroup = :group) " +
            "and (:search is null " +
            "or lower(i.nameEn) like lower(concat('%', :search, '%')) " +
            "or lower(i.nameRu) like lower(concat('%', :search, '%'))" +
            "or lower(i.subgroup) like lower(concat('%', :search, '%')))")
    List<Ingredient> findAllWithFilters(@Param("search") String search,
                                        @Param("spiritLow") byte spiritLow,
                                        @Param("spiritHigh") byte spiritHigh,
                                        @Param("group") String group);

}
