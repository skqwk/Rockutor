package ru.rockutor.signer.producer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.test.util.ReflectionTestUtils;
import ru.rockutor.sign.SignResult;
import ru.rockutor.sign.SignStatus;
import ru.rockutor.signer.config.KafkaProducerService;
import ru.rockutor.signer.domain.SignRequest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SignResultProducerImplTest {
    private static final String TOPIC_NAME = "topicName";
    private static final UUID RQ_ID = UUID.randomUUID();
    private static final UUID DOCUMENT_ID = UUID.randomUUID();


    private final KafkaProducerService kafkaProducerService = mock(KafkaProducerService.class);
    private final SignResultProducerImpl signResultProducer = new SignResultProducerImpl(kafkaProducerService);

    private final ArgumentCaptor<SignResult> signResultArgumentCaptor = ArgumentCaptor.forClass(SignResult.class);

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(signResultProducer, "topicName", TOPIC_NAME);
    }

    @Test
    void send() {
        // GIVEN
        SignRequest signRequest = new SignRequest();
        signRequest.setDocumentId(DOCUMENT_ID);
        signRequest.setId(RQ_ID);
        signRequest.setStatus(SignStatus.SIGNED);

        // WHEN
        signResultProducer.send(signRequest);

        // THEN
        verify(kafkaProducerService).send(eq(TOPIC_NAME), eq(RQ_ID.toString()),  signResultArgumentCaptor.capture());
        SignResult captured = signResultArgumentCaptor.getValue();
        assertEquals(DOCUMENT_ID, captured.getDocumentId());
        assertEquals(RQ_ID, captured.getRqId());
        assertEquals(SignStatus.SIGNED, captured.getStatus());
    }
}