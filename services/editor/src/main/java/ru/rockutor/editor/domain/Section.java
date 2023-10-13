package ru.rockutor.editor.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;
import java.util.UUID;

/**
 * Секция - элемент построения документа
 */
@Entity
@Table(name = "T_SECTION")
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Название секции
     */
    private String name;

    /**
     * Атрибуты секции
     */
    @OneToMany
    private List<Attribute> attributes;
}
