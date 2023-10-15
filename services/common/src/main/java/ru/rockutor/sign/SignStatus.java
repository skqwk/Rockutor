package ru.rockutor.sign;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Статус подписания
 */
@Getter
@RequiredArgsConstructor
public enum SignStatus {
    SIGNING("На подписании"),
    SIGNED("Подписан"),
    CANCELED("Отозван");

    private final String label;
}