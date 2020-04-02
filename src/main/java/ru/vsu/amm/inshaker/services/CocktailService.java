package ru.vsu.amm.inshaker.services;

import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.exceptions.CocktailNotFoundException;
import ru.vsu.amm.inshaker.model.Cocktail;
import ru.vsu.amm.inshaker.repositories.CocktailRepository;

import java.util.List;

@Service
public class CocktailService {

    private final CocktailRepository repository;

    public CocktailService(CocktailRepository repository) {
        this.repository = repository;
    }

    public List<Cocktail> getAll() {
        return repository.findAll();
    }

    public Cocktail get(Long id) {
        return repository.findById(id).orElseThrow(() -> new CocktailNotFoundException(id));
    }

}
