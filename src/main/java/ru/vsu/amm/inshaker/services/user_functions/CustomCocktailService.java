package ru.vsu.amm.inshaker.services.user_functions;

import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.model.Cocktail;
import ru.vsu.amm.inshaker.model.dto.CocktailDTO;
import ru.vsu.amm.inshaker.model.dto.CocktailDTOConverter;
import ru.vsu.amm.inshaker.model.dto.CocktailSimpleDTO;
import ru.vsu.amm.inshaker.model.user.User;
import ru.vsu.amm.inshaker.repositories.CocktailRepository;
import ru.vsu.amm.inshaker.services.CocktailService;
import ru.vsu.amm.inshaker.services.user.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomCocktailService {

    private final CocktailRepository cocktailRepository;
    private final CocktailService cocktailService;
    private final UserService userService;
    private final Mapper mapper;
    private final CocktailDTOConverter cocktailDTOConverter;

    public CustomCocktailService(CocktailRepository cocktailRepository,
                                 CocktailService cocktailService, UserService userService,
                                 Mapper mapper,
                                 CocktailDTOConverter cocktailDTOConverter) {
        this.cocktailRepository = cocktailRepository;
        this.cocktailService = cocktailService;
        this.userService = userService;
        this.mapper = mapper;
        this.cocktailDTOConverter = cocktailDTOConverter;
    }


    public CocktailDTO getCustom(Long id) {
        Cocktail cocktail = cocktailService.getCocktail(id);
        if (cocktail.getAuthor() == userService.getCurrentUser()) {
            return mapper.map(cocktail, CocktailDTO.class);
        } else throw new AccessDeniedException("The user does not have permission to get the cocktail " + id);
    }

    public List<CocktailSimpleDTO> getAllCustoms(String search, String base, String spirit, String group, List<String> tastes) {
        return cocktailService.getAllCocktails(userService.getCurrentUser(), search, base, spirit, group, tastes).stream()
                .map(c -> mapper.map(c, CocktailSimpleDTO.class)).collect(Collectors.toList());
    }


    public CocktailDTO addCustom(CocktailDTO cocktail) {
        Cocktail newCocktail = cocktailDTOConverter.convert(cocktail);
        newCocktail.setId(null);
        newCocktail.setAuthor(userService.getCurrentUser());
        return mapper.map(cocktailRepository.save(newCocktail), CocktailDTO.class);
    }

    public CocktailDTO updateCustom(CocktailDTO newCocktail, Long id) {
        Cocktail oldCocktail = cocktailService.getCocktail(id);
        User currentUser = userService.getCurrentUser();
        if (oldCocktail.getAuthor() == currentUser) {
            BeanUtils.copyProperties(cocktailDTOConverter.convert(newCocktail), oldCocktail);
            oldCocktail.setId(id);
            oldCocktail.setAuthor(currentUser);
            return mapper.map(cocktailRepository.save(oldCocktail), CocktailDTO.class);
        } else throw new AccessDeniedException("The user does not have permission to update the cocktail " + id);
    }

    public void deleteCustom(Long id) {
        Cocktail cocktail = cocktailService.getCocktail(id);
        if (cocktail.getAuthor() == userService.getCurrentUser()) {
            cocktailRepository.deleteById(id);
        } else throw new AccessDeniedException("The user does not have permission to delete the cocktail " + id);
    }

}
