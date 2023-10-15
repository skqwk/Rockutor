package ru.rockutor.sign;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

// TODO: shared-библиотека это не очень,
//  поскольку микросервисы превращаются в распределенный монолит,
//  в перспективе заменить на хранение схемы сообщений в kafka-schema-registry
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignTask implements Serializable {
    private UUID documentId;
    private String author;
}
