package com.example.user.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class XmlToJsonParser {

    public String convertXmlToJson(String xml) throws JsonProcessingException {
        XmlMapper xmlMapper = new XmlMapper();
        ObjectMapper jsonMapper = new ObjectMapper();
        Map<String, Object> map = xmlMapper.readValue(xml, Map.class);
        return jsonMapper.writeValueAsString(map);
    }
}
