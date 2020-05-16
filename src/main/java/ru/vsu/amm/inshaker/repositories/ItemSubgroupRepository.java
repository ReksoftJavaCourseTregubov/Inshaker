package ru.vsu.amm.inshaker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.amm.inshaker.model.item.properties.ItemSubgroup;

public interface ItemSubgroupRepository extends JpaRepository<ItemSubgroup, Long> {
}
