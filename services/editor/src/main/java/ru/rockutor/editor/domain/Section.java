package ru.rockutor.editor.domain;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Секция - элемент построения документа
 */
@Entity
@Getter
@Setter
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
    @OneToMany(cascade = CascadeType.ALL)
    private List<Attribute> attributes = new ArrayList<>();

    public void addAttribute(Attribute attribute) {
        Optional<Attribute> attributeWithSameNameOpt = attributes.stream()
                .filter(a -> a.getName().equals(attribute.getName()))
                .findFirst();

        if (attributeWithSameNameOpt.isPresent()) {
            Attribute attributeWithSameName = attributeWithSameNameOpt.get();
            attributeWithSameName.setType(attribute.getType());
            attributeWithSameName.setValue(attribute.getValue());
        } else {
            attributes.add(attribute);
        }
    }
}
