package ru.rockutor.editor.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.rockutor.editor.config.KafkaProducerService;
import ru.rockutor.editor.domain.Document;
import ru.rockutor.sign.SignTask;

@Service
@RequiredArgsConstructor
public class SignTaskProducerImpl implements SignTaskProducer {
    @Value("${sign.task.topic}")
    private String topicName;

    private final KafkaProducerService producerService;

    @Override
    public void send(Document document) {
        SignTask signTask = new SignTask(
                document.getId(),
                document.getAuthor()
        );

        producerService.send(topicName, document.getId().toString(), signTask);
    }
}
