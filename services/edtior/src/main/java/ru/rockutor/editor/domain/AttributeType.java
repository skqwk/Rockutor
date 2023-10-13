package ru.rockutor.editor.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AttributeType {

    STATIC_TEXT("Статический текст"),
    IMAGE("Изображение"),
    TABLE("Таблица");

    private final String name;
}
