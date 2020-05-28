package ru.vsu.amm.inshaker.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Spirit {
    STRONG((long) 1, "Крепкие", (byte) 26, (byte) 100),
    MEDIUM((long) 2, "Средние", (byte) 13, (byte) 25),
    LIGHT((long) 3, "Лёгкие", (byte) 1, (byte) 12),
    FREE((long) 4, "Безалкогольные", (byte) 0, (byte) 0);

    private final Long id;
    private final String name;

    @JsonIgnore
    private final byte rangeLow;
    @JsonIgnore
    private final byte rangeHigh;

    private static final Map<Long, Spirit> map;

    Spirit(Long id, String name, byte rangeLow, byte rangeHigh) {
        this.id = id;
        this.name = name;
        this.rangeLow = rangeLow;
        this.rangeHigh = rangeHigh;
    }

    static {
        map = new HashMap<>();
        for (Spirit s : Spirit.values()) {
            map.put(s.id, s);
        }
    }

    public static Spirit findById(Long s) {
        return map.getOrDefault(s, Spirit.FREE);
    }

    public static Spirit findBySpiritValue(byte spirit) {
        return Arrays.stream(Spirit.values())
                .filter(s -> s.getRangeLow() <= spirit && spirit <= s.getRangeHigh())
                .findFirst().orElse(Spirit.FREE);
    }

}
