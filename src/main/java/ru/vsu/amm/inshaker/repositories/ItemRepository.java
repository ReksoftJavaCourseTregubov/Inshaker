package ru.vsu.amm.inshaker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.amm.inshaker.model.item.Item;
import ru.vsu.amm.inshaker.model.item.properties.ItemSubgroup;

import java.util.List;

public interface ItemRepository<T extends Item> extends JpaRepository<T, Long> {

    List<Item> findAllByItemSubgroup(ItemSubgroup itemSubgroup);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM USER_BAR WHERE BAR_ID = :id", nativeQuery = true)
    void deleteUserBarByItemId(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE COCKTAIL SET GLASS_ID = null WHERE GLASS_ID = :id", nativeQuery = true)
    void nullifyCocktailGlassByItemId(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE COCKTAIL SET GARNISH_ID = null WHERE GARNISH_ID = :id", nativeQuery = true)
    void nullifyCocktailGarnishByItemId(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM MIXING_METHOD_TABLEWARE WHERE TABLEWARE_ID = :id", nativeQuery = true)
    void deleteMixingMethodTablewareByItemId(@Param("id") Long id);

}
