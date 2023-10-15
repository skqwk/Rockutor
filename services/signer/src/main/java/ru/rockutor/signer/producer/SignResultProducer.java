package ru.rockutor.signer.producer;

import ru.rockutor.signer.domain.SignRequest;

public interface SignResultProducer {
    void send(SignRequest request);
}
