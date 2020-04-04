package ru.vsu.amm.inshaker.services;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ru.vsu.amm.inshaker.controllers.CocktailController;
import ru.vsu.amm.inshaker.model.Cocktail;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CocktailModelAssembler implements RepresentationModelAssembler<Cocktail, EntityModel<Cocktail>> {

    @Override
    public EntityModel<Cocktail> toModel(Cocktail entity) {
        return new EntityModel<>(entity,
                linkTo(methodOn(CocktailController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(CocktailController.class).all(null, null, null, null, null)).withRel("cocktails"));
    }

    @Override
    public CollectionModel<EntityModel<Cocktail>> toCollectionModel(Iterable<? extends Cocktail> entities) {
        List<EntityModel<Cocktail>> models = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());
        Link link = linkTo(methodOn(CocktailController.class).all(null, null, null, null, null)).withSelfRel();
        return new CollectionModel<>(models, link);
    }

}
