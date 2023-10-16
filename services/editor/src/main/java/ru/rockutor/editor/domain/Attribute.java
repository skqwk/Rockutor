package ru.rockutor.editor.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Атрибут - минимальный элемент построения документа
 */
@Entity
@Getter
@Setter
@Table(name = "T_ATTRIBUTE")
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Тип атрибута
     */
    private AttributeType type;

    /**
     * Название атрибута
     */
    private String name;

    /**
     * Значение атрибута
     */
    private String value;
}
