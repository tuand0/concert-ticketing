package fr.istic.taa.jaxrs.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.ws.rs.ext.ContextResolver;

/**
 * Cette classe permet de configurer le serveur pour qu'il
 * puisse correctement gérer les dates au format JSON
 */
public class MyObjectMapperProvider implements ContextResolver<ObjectMapper> {

    ObjectMapper objectMapper = createObjectMapper();

    @Override
    public ObjectMapper getContext(final Class<?> type) {
        return objectMapper;
    }

    ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }
}