package ru.rockutor.editor.producer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.test.util.ReflectionTestUtils;
import ru.rockutor.editor.config.KafkaProducerService;
import ru.rockutor.editor.domain.Document;
import ru.rockutor.sign.SignTask;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SignTaskProducerImplTest {
    private static final String TOPIC_NAME = "topicName";
    private static final String AUTHOR = "author";
    private static final UUID DOCUMENT_ID = UUID.randomUUID();


    private final KafkaProducerService kafkaProducerService = mock(KafkaProducerService.class);
    private final SignTaskProducerImpl signTaskProducer = new SignTaskProducerImpl(kafkaProducerService);

    private final ArgumentCaptor<SignTask> signTaskArgumentCaptor = ArgumentCaptor.forClass(SignTask.class);

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(signTaskProducer, "topicName", TOPIC_NAME);
    }


    @Test
    void send() {
        // GIVEN
        Document document = new Document();
        document.setId(DOCUMENT_ID);
        document.setAuthor(AUTHOR);

        // WHEN
        signTaskProducer.send(document);

        // THEN
        verify(kafkaProducerService).send(eq(TOPIC_NAME), eq(DOCUMENT_ID.toString()),  signTaskArgumentCaptor.capture());
        SignTask captured = signTaskArgumentCaptor.getValue();
        assertEquals(DOCUMENT_ID, captured.getDocumentId());
        assertEquals(AUTHOR, captured.getAuthor());
    }
}