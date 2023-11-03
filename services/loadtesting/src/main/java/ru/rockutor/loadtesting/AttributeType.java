package ru.rockutor.loadtesting;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AttributeType {

    STATIC_TEXT("Статический текст"),
    IMAGE("Изображение"),
    TABLE("Таблица");

    private final String label;
}
