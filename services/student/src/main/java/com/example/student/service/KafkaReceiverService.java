package com.example.student.service;

import com.example.student.service.interfaces.StudentService;
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

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final StudentService studentService;

    @KafkaListener(topics = "${spring.kafka.request.topic}", groupId = "${spring.kafka.group-id}")
    public void listen(String message, @Header(KafkaHeaders.REPLY_TOPIC) String replyTopic) {
        // Обработка сообщения
        String response = processMessage(message);

        // Отправка ответа обратно в указанный топик
        ProducerRecord<String, String> replyRecord = new ProducerRecord<>(replyTopic, response);
        kafkaTemplate.send(replyRecord);
    }

    private String processMessage(String message) {

        if (message == null) {
            return studentService.getAllStudents().toString();
        } else {
            return studentService.getStudentByGradeBook(message).toString();
        }
    }
}
