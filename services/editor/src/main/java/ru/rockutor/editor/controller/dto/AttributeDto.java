package ru.rockutor.editor.controller.dto;

import java.util.HashMap;
import java.util.Map;

public record AttributeDto(String type,
                           String value) {
    private static final String TYPE = "type";
    private static final String VALUE = "value";

    public Map<String, Object> toMap() {
        Map<String, Object> converted = new HashMap<>(2);

        converted.put(TYPE, type);
        converted.put(VALUE, value);

        return converted;
    }
}
