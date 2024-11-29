package com.example.student.service;

import com.example.student.endpoint.StudentEndpoint;
import com.example.student.generatedxml.GetStudentRequest;
import com.example.student.generatedxml.GetStudentResponse;
import com.example.student.util.XmlSerializer;
import jakarta.xml.bind.JAXBException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@RequiredArgsConstructor
@Service
public class KafkaReceiverService {

    private static final Logger LOGGER = Logger.getLogger(KafkaReceiverService.class.getName());
    private final StudentEndpoint studentEndpoint;

    @KafkaListener(topics = "${spring.kafka.request.topic}", containerFactory = "kafkaListenerContainerFactory")
    @SendTo("students_reply")
    public String listen(@Payload() String request) {
        LOGGER.info("Received request: " + request);
        GetStudentResponse response = processMessage(request);
        try {
            var responseString = XmlSerializer.marshalToXml(response, GetStudentResponse.class);
            LOGGER.info("Send response: " + responseString);
            return responseString;
        } catch (JAXBException e) {
            var message = "Serialization to Xml failed";
            LOGGER.severe(message);
            throw new RuntimeException(message);
        }
    }

    private GetStudentResponse processMessage(String message) {
        if (message.isEmpty()) {
            return studentEndpoint.getStudents();
        }
        var request = new GetStudentRequest();
        request.setGradeBookNumber(message);
        return studentEndpoint.getStudent(request);
    }
}
