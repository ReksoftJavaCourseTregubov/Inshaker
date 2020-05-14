package ru.vsu.amm.inshaker.repositories;

import org.springframework.stereotype.Repository;
import ru.vsu.amm.inshaker.model.cocktail.Cocktail;
import ru.vsu.amm.inshaker.model.enums.Spirit;
import ru.vsu.amm.inshaker.model.item.Ingredient;
import ru.vsu.amm.inshaker.model.item.Item;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SearchRepository {

    private final EntityManager entityManager;

    public SearchRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Item> searchItems(String search, Long categoryId, Long groupId, Long subgroupId,
                                  Long baseId, Long countryId, Long spiritId, List<Long> tasteIds) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Item> query = builder.createQuery(Item.class);

        Root<Item> item = query.from(Item.class);
        Root<Ingredient> ingredient = builder.treat(item, Ingredient.class);

        List<Predicate> predicates = new ArrayList<>();

        if (subgroupId != null) {
            predicates.add(builder.equal(item.get("itemSubgroup").get("id"), subgroupId));
        } else if (groupId != null) {
            predicates.add(builder.equal(item.get("itemSubgroup").get("itemGroup").get("id"), groupId));
        } else if (categoryId != null) {
            predicates.add(builder.equal(item.get("itemSubgroup").get("itemGroup").get("itemCategory").get("id"), categoryId));
        }

        if (baseId != null) {
            predicates.add(builder.equal(ingredient.get("ingredientBase").get("id"), baseId));
        }

        if (countryId != null) {
            predicates.add(builder.equal(ingredient.get("country").get("id"), countryId));
        }

        if (spiritId != null) {
            predicates.add(spiritPredicate(builder, item, spiritId));
        }

        if (tasteIds != null) {
            tasteIds.forEach(s -> predicates.add(builder.equal(ingredient.join("taste").get("id"), s)));
        }

        if (search != null) {
            Predicate itemGroupPredicate = builder.like(builder.lower(item.get("itemSubgroup").get("itemGroup").get("name")), "%" + search.toLowerCase() + "%");
            predicates.add(builder.or(searchPredicate(builder, item, search, "itemSubgroup"), itemGroupPredicate));
        }

        query.select(item).where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(query).getResultList();
    }


    public List<Cocktail> searchCocktails(String search, Long baseId, Long groupId, Long subgroupId,
                                          Long spiritId, Long mixingMethodId, List<Long> tasteIds) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cocktail> query = builder.createQuery(Cocktail.class);

        Root<Cocktail> cocktail = query.from(Cocktail.class);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(builder.isNull(cocktail.get("author")));

        if (baseId != null) {
            predicates.add(builder.equal(cocktail.join("recipePart").get("ingredient").get("id"), baseId));
            predicates.add(builder.equal(cocktail.join("recipePart").get("isBase"), true));
        }

        if (subgroupId != null) {
            predicates.add(builder.equal(cocktail.get("itemSubgroup").get("id"), subgroupId));
        }

        if (groupId != null) {
            predicates.add(builder.equal(cocktail.get("cocktailGroup").get("id"), groupId));
        }

        if (spiritId != null) {
            predicates.add(spiritPredicate(builder, cocktail, spiritId));
        }

        if (mixingMethodId != null) {
            predicates.add(builder.equal(cocktail.get("mixingMethod").get("id"), mixingMethodId));
        }

        if (tasteIds != null) {
            tasteIds.forEach(s -> predicates.add(builder.equal(cocktail.join("taste").get("id"), s)));
        }

        if (search != null) {
            predicates.add(searchPredicate(builder, cocktail, search, "cocktailSubgroup"));
        }

        query.select(cocktail).where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(query).getResultList();
    }

    private Predicate searchPredicate(CriteriaBuilder builder, Root<?> root, String search, String subgroupClass) {
        Predicate nameRuPredicate = builder.like(builder.lower(root.get("nameRu")), "%" + search.toLowerCase() + "%");
        Predicate nameEnPredicate = builder.like(builder.lower(root.get("nameEn")), "%" + search.toLowerCase() + "%");
        Predicate itemSubgroupPredicate = builder.like(builder.lower(root.get(subgroupClass).get("name")), "%" + search.toLowerCase() + "%");
        return builder.or(nameRuPredicate, nameEnPredicate, itemSubgroupPredicate);
    }

    private Predicate spiritPredicate(CriteriaBuilder builder, Root<?> root, Long spiritId) {
        Spirit spirit = Spirit.findById(spiritId);
        return (builder.between(root.get("spirit"), spirit.getRangeLow(), spirit.getRangeHigh()));
    }

}
