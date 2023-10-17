package ru.rockutor.signer.config;

import org.junit.jupiter.api.Test;
import ru.rockutor.sign.SignTask;
import ru.rockutor.signer.usecase.CreateSignRequestUseCase;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class KafkaTopicListenerTest {
    private static final String AUTHOR = "author";
    private static final UUID DOCUMENT_ID = UUID.randomUUID();

    private final CreateSignRequestUseCase createSignRequestUseCase = mock(CreateSignRequestUseCase.class);
    private final KafkaTopicListener kafkaTopicListener = new KafkaTopicListener(createSignRequestUseCase);
    @Test
    void consume() {
        // GIVEN
        SignTask signTask = new SignTask();
        signTask.setAuthor(AUTHOR);
        signTask.setDocumentId(DOCUMENT_ID);

        // WHEN
        kafkaTopicListener.consume(signTask);

        // THEN
        verify(createSignRequestUseCase).createSignRequest(DOCUMENT_ID, AUTHOR);
    }
}