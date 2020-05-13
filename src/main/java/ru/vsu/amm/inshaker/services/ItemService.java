package ru.vsu.amm.inshaker.services;

import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.exceptions.AnonymousAuthenticationException;
import ru.vsu.amm.inshaker.exceptions.notfound.ItemNotFoundException;
import ru.vsu.amm.inshaker.dto.properties.IngredientPropertiesDTO;
import ru.vsu.amm.inshaker.dto.simple.ItemDTO;
import ru.vsu.amm.inshaker.dto.simple.ItemGroupedDTO;
import ru.vsu.amm.inshaker.dto.converters.ItemMapper;
import ru.vsu.amm.inshaker.model.item.Ingredient;
import ru.vsu.amm.inshaker.model.item.Item;
import ru.vsu.amm.inshaker.repositories.ItemRepository;
import ru.vsu.amm.inshaker.repositories.SearchRepository;
import ru.vsu.amm.inshaker.services.user.UserService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService<T extends Item> {

    private final UserService userService;
    private final PropertiesService propertiesService;
    private final ItemRepository<T> itemRepository;
    private final ItemMapper mapper;
    private final SearchRepository searchRepository;

    public ItemService(UserService userService,
                       PropertiesService propertiesService,
                       ItemRepository<T> itemRepository,
                       ItemMapper mapper,
                       SearchRepository searchRepository) {
        this.userService = userService;
        this.propertiesService = propertiesService;
        this.itemRepository = itemRepository;
        this.mapper = mapper;
        this.searchRepository = searchRepository;
    }

    public T getItem(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));
    }

    public Item getOne(Long id) {
        Item item = getItem(id);

        if (item instanceof Ingredient) {
            try {
                if (userService.getCurrentUser().getBar().contains(item)) {
                    ((Ingredient) item).setInBar(true);
                }
            } catch (AnonymousAuthenticationException ignored) {
            }
        }

        return getItem(id);
    }

    public List<ItemGroupedDTO> getAll() {
        return itemRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(i -> i.getItemSubgroup().getItemGroup()))
                .entrySet()
                .stream()
                .map(m -> new ItemGroupedDTO(m.getKey().getId(), m.getKey().getName(),
                        m.getValue()
                                .stream()
                                .map(mapper::map)
                                .sorted(Comparator.comparing(ItemDTO::getId))
                                .collect(Collectors.toList())))
                .sorted(Comparator.comparing(ItemGroupedDTO::getGroupId))
                .collect(Collectors.toList());
    }

    public List<ItemDTO> getAll(String search, Long categoryId, Long groupId, Long subgroupId,
                                Long baseId, Long countryId, Long spiritId, List<Long> tasteIds) {
        return searchRepository.searchItems(search, categoryId, groupId, subgroupId, baseId, countryId, spiritId, tasteIds)
                .stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    public IngredientPropertiesDTO getProperties() {
        return propertiesService.getIngredientProperties();
    }


    public T add(T item) {
        return null;
    }

    public T update(Long id, T item) {
        return null;
    }

    public void delete(Long id) {
        itemRepository.deleteById(id);
    }

}
