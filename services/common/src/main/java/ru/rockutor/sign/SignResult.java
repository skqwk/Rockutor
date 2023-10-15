package ru.rockutor.sign;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignResult implements Serializable {
    private UUID documentId;
    private UUID rqId;
    private SignStatus status;
}
