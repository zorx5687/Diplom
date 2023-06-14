package ru.chernykh.backend.model;

import java.util.NoSuchElementException;

public enum Category {
    BEADS("БИСЕР"),
    CLAY("ГЛИНА"),
    EMBROIDERY("ВЫШИКВКА"),
    IRON("ЖЕЛЕЗО"),
    KNITTING("ВЯЗАНИЕ"),
    MODELING("ЛЕПКА"),
    WOOD("ДЕРЕВО"),
    OTHER("ДРУГОЕ");
    private final String value;

    Category(String name) {
        this.value = name;
    }

    public String getValue() {
        return value;
    }

    public static Category findByName(String name) {
        for (int i = 0; i < values().length; i++) {
            if (values()[i].value.equals(name)) {
                return values()[i];
            }
        }
        throw new NoSuchElementException("Категория " + name + " не найдена");
    }
}
