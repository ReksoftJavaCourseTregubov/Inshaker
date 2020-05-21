package ru.vsu.amm.inshaker.services.items;

import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.dto.converters.items.ItemMapper;
import ru.vsu.amm.inshaker.dto.entire.ItemGroupedDTO;
import ru.vsu.amm.inshaker.dto.entire.items.IngredientDTO;
import ru.vsu.amm.inshaker.dto.entire.items.ItemDTO;
import ru.vsu.amm.inshaker.dto.properties.IngredientPropertiesDTO;
import ru.vsu.amm.inshaker.exceptions.AnonymousAuthenticationException;
import ru.vsu.amm.inshaker.exceptions.notfound.ItemNotFoundException;
import ru.vsu.amm.inshaker.model.item.Ingredient;
import ru.vsu.amm.inshaker.model.item.Item;
import ru.vsu.amm.inshaker.repositories.ItemRepository;
import ru.vsu.amm.inshaker.repositories.ItemSubgroupRepository;
import ru.vsu.amm.inshaker.repositories.SearchRepository;
import ru.vsu.amm.inshaker.services.PropertiesService;
import ru.vsu.amm.inshaker.services.factory.ItemFactory;
import ru.vsu.amm.inshaker.services.user.UserService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService<T extends Item, S extends ItemDTO> {

    private final UserService userService;
    private final PropertiesService propertiesService;
    private final SearchRepository searchRepository;
    private final ItemSubgroupRepository itemSubgroupRepository;
    private final ItemRepository<T> itemRepository;
    private final ItemMapper<T, S> itemMapper;
    private final ItemFactory<T, S> itemFactory;

    public ItemService(UserService userService,
                       PropertiesService propertiesService,
                       SearchRepository searchRepository,
                       ItemSubgroupRepository itemSubgroupRepository,
                       ItemRepository<T> itemRepository,
                       ItemMapper<T, S> itemMapper,
                       ItemFactory<T, S> itemFactory) {
        this.userService = userService;
        this.propertiesService = propertiesService;
        this.searchRepository = searchRepository;
        this.itemSubgroupRepository = itemSubgroupRepository;
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
        this.itemFactory = itemFactory;
    }

    public T getItem(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));
    }

    public S getOne(Long id) {
        T item = getItem(id);
        S itemDTO = itemMapper.map(item);

        if (item instanceof Ingredient) {
            try {
                if (userService.getCurrentUser().getBar().contains(item)) {
                    ((IngredientDTO) itemDTO).setInBar(true);
                }
            } catch (AnonymousAuthenticationException ignored) {
            }
        }

        return itemDTO;
    }

    public List<ItemGroupedDTO> getAll() {
        return itemRepository.findAll()
                .stream()
                .filter(i -> i.getItemSubgroup() != null)
                .collect(Collectors.groupingBy(i -> i.getItemSubgroup().getItemGroup()))
                .entrySet()
                .stream()
                .map(m -> new ItemGroupedDTO(m.getKey().getId(), m.getKey().getName(),
                        m.getValue()
                                .stream()
                                .map(itemMapper::mapSimple)
                                .sorted(Comparator.comparing(ItemDTO::getId))
                                .collect(Collectors.toList())))
                .sorted(Comparator.comparing(ItemGroupedDTO::getGroupId))
                .collect(Collectors.toList());
    }

    public List<ItemDTO> getAll(String search, Long categoryId, Long groupId, Long subgroupId,
                                Long baseId, Long countryId, Long spiritId, List<Long> tasteIds) {
        return searchRepository.searchItems(search, categoryId, groupId, subgroupId, baseId, countryId, spiritId, tasteIds)
                .stream()
                .map(itemMapper::mapSimple)
                .collect(Collectors.toList());
    }

    public IngredientPropertiesDTO getProperties() {
        return propertiesService.getIngredientProperties();
    }


    public S add(S item) {
        item.setId(null);
        T newItem = itemFactory.createItem();
        itemMapper.map(item, newItem);
        return itemMapper.map(itemRepository.save(newItem));
    }

    public S update(Long id, S item) {
        T oldItem = getItem(id);
        item.setId(id);
        itemMapper.map(item, oldItem);
        return itemMapper.map(itemRepository.save(oldItem));
    }

    public void delete(Long id) {
        T item = getItem(id);
        itemRepository.delete(item);
        if (item.getItemSubgroup() != null & itemRepository.findAllByItemSubgroup(item.getItemSubgroup()).isEmpty()) {
            itemSubgroupRepository.delete(item.getItemSubgroup());
        }
    }

}
