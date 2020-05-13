package ru.vsu.amm.inshaker.repositories;

import org.springframework.stereotype.Repository;
import ru.vsu.amm.inshaker.model.RecipePart;
import ru.vsu.amm.inshaker.model.Taste;
import ru.vsu.amm.inshaker.model.cocktail.Cocktail;
import ru.vsu.amm.inshaker.model.item.Ingredient;
import ru.vsu.amm.inshaker.model.item.properties.ItemSubgroup;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class PropertiesRepository {

    private final EntityManager entityManager;

    public PropertiesRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public <T> Optional<T> findById(Class<T> cls, Long id) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(cls);

        Root<T> root = query.from(cls);
        query.select(root).where(builder.equal(root.get("id"), id));

        return entityManager.createQuery(query).getResultList().stream().findFirst();
    }

    public <T> List<T> findAllDistinct(Class<T> cls) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(cls);

        Root<T> root = query.from(cls);
        return entityManager.createQuery(query.select(root).distinct(true)).getResultList();
    }

    public List<ItemSubgroup> findDistinctCocktailBases() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ItemSubgroup> query = builder.createQuery(ItemSubgroup.class);

        Root<RecipePart> root = query.from(RecipePart.class);
        query.select(root.get("ingredient").get("itemSubgroup"))
                .where(builder.equal(root.get("isBase"), true))
                .distinct(true);

        return entityManager.createQuery(query).getResultList();
    }

    public List<Taste> findDistinctCocktailTastes() {
        return findDistinctTastes(Cocktail.class);
    }

    public List<Taste> findDistinctIngredientTastes() {
        return findDistinctTastes(Ingredient.class);
    }

    private <T> List<Taste> findDistinctTastes(Class<T> cls) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Taste> query = builder.createQuery(Taste.class);

        Root<T> root = query.from(cls);
        query.select(root.get("taste")).distinct(true);

        return entityManager.createQuery(query).getResultList();
    }

}
