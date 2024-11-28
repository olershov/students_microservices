package com.example.student.service;

import com.example.student.endpoint.StudentEndpoint;
import com.example.student.generatedxml.GetStudentRequest;
import com.example.student.generatedxml.GetStudentResponse;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KafkaReceiverService {

    private final KafkaTemplate<String, GetStudentResponse> kafkaTemplate;
    private final StudentEndpoint studentEndpoint;

    @KafkaListener(topics = "${spring.kafka.request.topic}", groupId = "${spring.kafka.group-id}")
    public void listen(String message, @Header(KafkaHeaders.REPLY_TOPIC) String replyTopic) {
        var response = processMessage(message);
        ProducerRecord<String, GetStudentResponse> replyRecord = new ProducerRecord<>(replyTopic, response);
        kafkaTemplate.send(replyRecord);
    }

    private GetStudentResponse processMessage(String message) {
         var request = new GetStudentRequest();
         request.setGradeBookNumber(message);
         return studentEndpoint.getStudent(request);
    }
}
