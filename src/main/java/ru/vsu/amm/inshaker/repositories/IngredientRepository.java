package ru.vsu.amm.inshaker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.vsu.amm.inshaker.model.Ingredient;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    @Query("select distinct t from Ingredient i inner join i.taste t")
    List<String> findDistinctTastes();

    @Query("select distinct i.ingredientGroup from Ingredient i")
    List<String> findDistinctIngredientGroups();

}
