package org.texttechnologylab.utilities.documentation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.jaxrs.Reader;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.models.Swagger;
import org.reflections.Reflections;

import java.util.Set;

/**
 * Created by abrami on 06.06.16.
 */
public class SwaggerParser {

    public static String getSwaggerJson(String packageName) throws JsonProcessingException {
        Swagger swagger = getSwagger(packageName);
        String json = swaggerToJson(swagger);
        return json;
    }

    public static Swagger getSwagger(String packageName) {
        Reflections reflections = new Reflections(packageName);
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setResourcePackage(packageName);
        beanConfig.setScan(true);

        Swagger swagger = beanConfig.getSwagger();

        Reader reader = new Reader(swagger);

        Set<Class<?>> apiClasses = reflections.getTypesAnnotatedWith(Api.class);
        return reader.read(apiClasses);
    }

    public static String swaggerToJson(Swagger swagger) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        String json = objectMapper.writeValueAsString(swagger);
        return json;
    }

}