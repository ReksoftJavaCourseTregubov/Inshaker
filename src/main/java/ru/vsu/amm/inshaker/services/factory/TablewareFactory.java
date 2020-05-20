package ru.vsu.amm.inshaker.services.factory;

import org.springframework.stereotype.Service;
import ru.vsu.amm.inshaker.dto.entire.items.TablewareDTO;
import ru.vsu.amm.inshaker.model.item.Tableware;

@Service
public class TablewareFactory extends ItemFactory<Tableware, TablewareDTO>{

    @Override
    public Tableware createItem() {
        return new Tableware();
    }

    @Override
    public TablewareDTO createItemDTO() {
        return new TablewareDTO();
    }

}
