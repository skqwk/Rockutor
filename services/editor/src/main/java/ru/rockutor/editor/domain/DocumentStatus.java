package ru.rockutor.editor.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Статус документа
 */
@Getter
@RequiredArgsConstructor
public enum DocumentStatus {
    DRAFT("Черновик"),
    SIGNING("На подписании"),
    SIGNED("Подписан"),
    REFUSE("Отказ");

    private final String label;
}
