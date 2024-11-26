package com.example.student.model.enums;

import lombok.Getter;

@Getter
public enum Faculty {
    ECONOMIC("Факультет экономики"), JURIDICAL("Юридический факультет"), CHEMICAL("Факултет химии");

    private final String name;

    Faculty(String name) {
        this.name = name;
    }
}
