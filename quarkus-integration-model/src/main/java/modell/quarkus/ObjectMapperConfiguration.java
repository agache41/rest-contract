package modell.quarkus;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

@ApplicationScoped
public class ObjectMapperConfiguration {

    @Singleton
    @Produces
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        Hibernate6Module hibernate6Module = new Hibernate6Module();
        // fixing: JsonMappingException: failed to lazily initialize a collection of role:
        hibernate6Module.configure(Hibernate6Module.Feature.FORCE_LAZY_LOADING, false);
        hibernate6Module.configure(Hibernate6Module.Feature.REPLACE_PERSISTENT_COLLECTIONS, true);
        hibernate6Module.configure(Hibernate6Module.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS, false);
        hibernate6Module.configure(Hibernate6Module.Feature.REQUIRE_EXPLICIT_LAZY_LOADING_MARKER, true);

        objectMapper.registerModule(hibernate6Module);
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.ANY);
        objectMapper.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.ANY);
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        return objectMapper;
    }
}