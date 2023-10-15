package ru.rockutor.signer.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.rockutor.sign.SignResult;
import ru.rockutor.signer.config.KafkaProducerService;
import ru.rockutor.signer.domain.SignRequest;

@Service
@RequiredArgsConstructor
public class SignResultProducerImpl implements SignResultProducer {
    @Value("${sign.result.topic}")
    private String topicName;

    private final KafkaProducerService producerService;

    @Override
    public void send(SignRequest request) {


        SignResult result = new SignResult(
                request.getDocumentId(),
                request.getId(),
                request.getStatus()
        );

        producerService.send(topicName, request.getId().toString(), result);
    }
}
