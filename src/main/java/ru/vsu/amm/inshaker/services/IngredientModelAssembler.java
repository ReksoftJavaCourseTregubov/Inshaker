package ru.vsu.amm.inshaker.services;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ru.vsu.amm.inshaker.controllers.IngredientController;
import ru.vsu.amm.inshaker.model.Ingredient;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class IngredientModelAssembler implements RepresentationModelAssembler<Ingredient, EntityModel<Ingredient>> {

    @Override
    public EntityModel<Ingredient> toModel(Ingredient entity) {
        return new EntityModel<>(entity,
                linkTo(methodOn(IngredientController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(IngredientController.class).all()).withRel("ingredients"));
    }

    @Override
    public CollectionModel<EntityModel<Ingredient>> toCollectionModel(Iterable<? extends Ingredient> entities) {
        List<EntityModel<Ingredient>> models = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());
        Link link = linkTo(methodOn(IngredientController.class).all()).withSelfRel();
        return new CollectionModel<>(models, link);
    }

    public CollectionModel<String> tastesToCollectionModel(Iterable<String> entities) {
        return new CollectionModel<>(entities,
                linkTo(methodOn(IngredientController.class).tastes()).withSelfRel(),
                linkTo(methodOn(IngredientController.class).all()).withRel("ingredients"));
    }

    public CollectionModel<String> groupsToCollectionModel(Iterable<String> entities) {
        return new CollectionModel<>(entities,
                linkTo(methodOn(IngredientController.class).groups()).withSelfRel(),
                linkTo(methodOn(IngredientController.class).all()).withRel("ingredients"));
    }

    public CollectionModel<String> spiritsToCollectionModel(Iterable<String> entities) {
        return new CollectionModel<>(entities,
                linkTo(methodOn(IngredientController.class).groups()).withSelfRel(),
                linkTo(methodOn(IngredientController.class).all()).withRel("ingredients"));
    }

}
