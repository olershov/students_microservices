package com.example.user.service.impl;

import com.example.user.service.KafkaSenderService;
import com.example.user.service.interfaces.StudentService;
import com.example.user.util.XmlToJsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@AllArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final KafkaSenderService kafkaSenderService;
    private final XmlToJsonParser xmlToJsonParser;
    private static final Logger LOGGER = Logger.getLogger(StudentServiceImpl.class.getName());

    @Override
    public String getAllStudents() {
        var xml = kafkaSenderService.sendSyncRequest("");
        try {
            return xmlToJsonParser.convertXmlToJson(xml);
        } catch (JsonProcessingException e) {
            var message = "Error while parsing XML";
            LOGGER.severe(message);
            throw new RuntimeException(message);
        }
    }

    @Override
    public String getStudentByGradeBook(String gradeBookNumber) {
        var xml = kafkaSenderService.sendSyncRequest(gradeBookNumber);
        try {
            return xmlToJsonParser.convertXmlToJson(xml);
        } catch (JsonProcessingException e) {
            var message = "Error while parsing XML";
            LOGGER.severe(message);
            throw new RuntimeException(message);
        }
    }
}
