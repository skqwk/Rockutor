package ru.rockutor.editor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rockutor.editor.domain.Document;

import java.util.UUID;

/**
 * Репозиторий для работы с документами
 */
public interface DocumentRepo extends JpaRepository<Document, UUID> {

}
