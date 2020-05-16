package ru.vsu.amm.inshaker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.amm.inshaker.model.item.Item;
import ru.vsu.amm.inshaker.model.item.properties.ItemSubgroup;

import java.util.List;

public interface ItemRepository<T extends Item> extends JpaRepository<T, Long> {

    List<Item> findAllByItemSubgroup(ItemSubgroup itemSubgroup);

}
