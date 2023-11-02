package ru.rockutor.loadtesting;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Attribute {
    private final String name;
    private final String type;
    private final String value;
}
