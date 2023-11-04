package ru.rockutor.editor.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.rockutor.editor.domain.DocumentStatus;
import ru.rockutor.editor.usecase.UpdateDocumentStatusUseCase;
import ru.rockutor.sign.SignResult;
import ru.rockutor.sign.SignStatus;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "kafka.enabled")
public class KafkaTopicListener {
    private static final Map<SignStatus, DocumentStatus> SIGN_STATUS_DOCUMENT_STATUS =
            Map.of(SignStatus.SIGNING, DocumentStatus.SIGNING,
                    SignStatus.SIGNED, DocumentStatus.SIGNED,
                    SignStatus.CANCELED, DocumentStatus.REFUSE);

    private final UpdateDocumentStatusUseCase updateDocumentStatusUseCase;


    @KafkaListener(topics = {"${sign.result.topic}"}, groupId = "${sign.groupId}")
    public void consume(SignResult signResult) {
        log.info("Получен результат подписания для документа с id=[{}], статус=[{}]",
                signResult.getDocumentId(), signResult.getStatus());

        DocumentStatus newStatus = SIGN_STATUS_DOCUMENT_STATUS.get(signResult.getStatus());
        updateDocumentStatusUseCase.updateStatus(signResult.getDocumentId(), newStatus);
    }
}
