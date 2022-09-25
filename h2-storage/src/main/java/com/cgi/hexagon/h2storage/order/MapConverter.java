package com.cgi.hexagon.h2storage.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.Map;

public class MapConverter implements AttributeConverter<Map<String, Object>, String> {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Object> metadata) {
        String metadataJson = null;
        if (null != metadata) {
            try {
                metadataJson = objectMapper.writeValueAsString(metadata);
            } catch (final JsonProcessingException e) {}
        }
        return metadataJson;
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String metadataJSON) {
        Map<String, Object> metadata = null;
        if (null != metadataJSON) {
            try {
                metadata = objectMapper.readValue(metadataJSON, Map.class);
            } catch (final IOException e) {
            }
        }

        return metadata;
    }
}
