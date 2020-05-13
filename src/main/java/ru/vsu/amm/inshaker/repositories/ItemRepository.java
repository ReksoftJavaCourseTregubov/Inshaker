package ru.vsu.amm.inshaker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.amm.inshaker.model.item.Item;

public interface ItemRepository<T extends Item> extends JpaRepository<T, Long> {
}
