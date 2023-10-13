package ru.rockutor.editor.domain;

import lombok.RequiredArgsConstructor;

/**
 * Статус документа
 */
@RequiredArgsConstructor
public enum DocumentStatus {
    DRAFT("Черновик"),
    SIGNING("На подписании"),
    SIGNED("Подписан"),
    REFUSE("Отказ");

    private final String name;
}
