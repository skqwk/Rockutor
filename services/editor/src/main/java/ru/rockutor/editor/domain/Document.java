package ru.rockutor.editor.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    @OneToMany(cascade = CascadeType.ALL)
    private List<Section> sections = new ArrayList<>();

    @Transient
    public Optional<Section> getSectionByName(String sectionName) {
        return getSections()
                .stream()
                .filter(s -> s.getName().equals(sectionName))
                .findFirst();
    }

    public void addSection(Section section) {
        sections.add(section);
    }
}
