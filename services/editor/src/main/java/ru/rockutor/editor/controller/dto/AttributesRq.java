package ru.rockutor.editor.controller.dto;

import java.util.HashMap;
import java.util.Map;

public record AttributesRq(Map<String, AttributeDto> attributes) {
    public Map<String, Map<String, Object>> toMap() {
        Map<String, Map<String, Object>> converted = new HashMap<>();
        for (String key : this.attributes.keySet()) {
            AttributeDto attributeDto = this.attributes.get(key);
            converted.put(key, attributeDto.toMap());
        }

        return converted;
    }
}
