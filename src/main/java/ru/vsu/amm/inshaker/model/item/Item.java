package ru.vsu.amm.inshaker.model.item;

import lombok.Data;
import org.hibernate.validator.constraints.URL;
import ru.vsu.amm.inshaker.model.item.properties.ItemSubgroup;

import javax.persistence.*;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    private String nameRu;
    private String nameEn;

    @ManyToOne
    private ItemSubgroup itemSubgroup;

    private String legend;

    @URL
    private String imageRef;

    @Column(name = "DTYPE", insertable = false, updatable = false)
    private String dType;

}
