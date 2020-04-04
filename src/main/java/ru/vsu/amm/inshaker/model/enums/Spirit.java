package ru.vsu.amm.inshaker.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum Spirit {
    STRONG("Крепкие", (byte) 26, (byte) 100),
    MEDIUM("Средние", (byte) 13, (byte) 25),
    LIGHT("Лёгкие", (byte) 1, (byte) 12),
    FREE("Безалкогольные", (byte) 0, (byte) 0),
    TABLEWARE("Посуда", (byte) -1, (byte) -1),
    ANY("Любые", Byte.MIN_VALUE, Byte.MAX_VALUE);

    private final String ruName;

    private final byte rangeLow;
    private final byte rangeHigh;

    private static final Map<String, Spirit> map;

    Spirit(String ruName, byte rangeLow, byte rangeHigh) {
        this.ruName = ruName;
        this.rangeLow = rangeLow;
        this.rangeHigh = rangeHigh;
    }

    static {
        map = new HashMap<>();
        for (Spirit s : Spirit.values()) {
            map.put(s.ruName, s);
        }
    }

    public static Spirit findByRuName(String s) {
        return map.getOrDefault(s, Spirit.ANY);
    }

    public String getRuName() {
        return ruName;
    }

    public byte getRangeLow() {
        return rangeLow;
    }

    public byte getRangeHigh() {
        return rangeHigh;
    }

}
