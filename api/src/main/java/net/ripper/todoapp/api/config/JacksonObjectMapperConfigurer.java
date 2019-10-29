package net.ripper.todoapp.api.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonObjectMapperConfigurer {
    /**
     * Custom ObjectMapper
     * adds {@link JsonNullableModule} to serialize/deserialize values wrapped by
     * {@link org.openapitools.jackson.nullable.JsonNullable}
     *
     * @return
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.registerModule(new JsonNullableModule());
        return mapper;
    }
}
