package ru.vsu.amm.inshaker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.amm.inshaker.model.Cocktail;

public interface CocktailRepository extends JpaRepository<Cocktail, Long> {
}
