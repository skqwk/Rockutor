package ru.rockutor.editor.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Документ
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "T_DOCUMENT")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Автор документа
     */
    private String author;

    /**
     * Дата создания документа
     */
    private Instant createdAt;

    /**
     * Статус документа
     */
    private DocumentStatus status;

    /**
     * Секции документа
     */
    @OneToMany
    private List<Section> sections = new ArrayList<>();
}
