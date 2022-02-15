package com.project.momo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.momo.dto.error.ErrorMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class JsonConverter {

    @PostConstruct
    public void postConstruct() {
        this.staticObjectMapper = this.objectMapper;
        System.out.println(staticObjectMapper);
    }

    private static ObjectMapper staticObjectMapper;
    private final ObjectMapper objectMapper;

    public static String convert(String message) throws JsonProcessingException {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto(message);
        System.out.println(errorMessageDto);
        System.out.println(staticObjectMapper);
        return staticObjectMapper.writeValueAsString(errorMessageDto);
    }

    public static String mapToJson(Map map) throws JsonProcessingException {
        return staticObjectMapper.writeValueAsString(map);
    }

}
