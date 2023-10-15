package ru.rockutor.signer.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.rockutor.sign.SignTask;
import ru.rockutor.signer.usecase.CreateSignRequestUseCase;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaTopicListener {
    private final CreateSignRequestUseCase createSignRequestUseCase;

    @KafkaListener(topics = {"${sign.task.topic}"}, groupId = "${sign.groupId}")
    public void consume(SignTask signTask) {
        log.info("Получена задача на подписание для документа с id=[{}], автор=[{}]",
                signTask.getDocumentId(), signTask.getAuthor());

        createSignRequestUseCase.createSignRequest(signTask.getDocumentId(), signTask.getAuthor());
    }
}
