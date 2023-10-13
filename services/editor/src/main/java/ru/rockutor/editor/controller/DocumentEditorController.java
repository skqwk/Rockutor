package ru.rockutor.editor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rockutor.editor.controller.dto.AttributeDto;
import ru.rockutor.editor.controller.dto.CreateDocumentRs;
import ru.rockutor.editor.controller.dto.DocumentDto;
import ru.rockutor.editor.controller.dto.GetDocumentRs;
import ru.rockutor.editor.controller.dto.SectionDto;
import ru.rockutor.editor.controller.dto.SignDocumentRs;
import ru.rockutor.editor.domain.Attribute;
import ru.rockutor.editor.domain.Document;
import ru.rockutor.editor.domain.DocumentStatus;
import ru.rockutor.editor.domain.Section;
import ru.rockutor.editor.usecase.CreateDocumentUseCase;
import ru.rockutor.editor.usecase.GetDocumentUseCase;
import ru.rockutor.editor.usecase.SendForSigningUseCase;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static ru.rockutor.editor.util.Formatter.format;

@RestController
@RequiredArgsConstructor
public class DocumentEditorController {
    private final CreateDocumentUseCase createDocumentUseCase;
    private final SendForSigningUseCase sendForSigningUseCase;
    private final GetDocumentUseCase getDocumentUseCase;

    @PostMapping("/document")
    public CreateDocumentRs createDocument() {
        String author = ""; // Получение из auth
        Document created = createDocumentUseCase.createDocument(author);
        return new CreateDocumentRs(
                created.getId(),
                created.getAuthor(),
                format(created.getCreatedAt())
        );
    }

    @GetMapping("/document/{id}")
    public GetDocumentRs getDocument(@PathVariable UUID id) {
        Document document = getDocumentUseCase.getDocument(id);
        return new GetDocumentRs(toDocumentDto(document));
    }

    //<editor-fold desc="DocumentDto mapping">
    private DocumentDto toDocumentDto(Document document) {
        return new DocumentDto(document.getId(), toSectionsDto(document.getSections()));
    }

    private List<SectionDto> toSectionsDto(List<Section> sections) {
        return sections.stream().map(this::toSectionDto).collect(Collectors.toList());
    }

    private SectionDto toSectionDto(Section section) {
        return new SectionDto(section.getName(), toAttributesDto(section.getAttributes()));
    }

    private Map<String, AttributeDto> toAttributesDto(List<Attribute> attributes) {
        return attributes.stream().collect(Collectors.toMap(Attribute::getName, this::toAttributeDto));
    }

    private AttributeDto toAttributeDto(Attribute attribute) {
        return new AttributeDto(attribute.getType().name(), attribute.getValue());
    }
    //</editor-fold>

    @PostMapping("/sendForSigning/{id}")
    public SignDocumentRs sendForSigning(@PathVariable UUID id) {
        DocumentStatus status = sendForSigningUseCase.sendForSigning(id);
        return new SignDocumentRs(
                status.getLabel(),
                status.name()
        );
    }
}
