package com.example.student.service;

import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaService {

    @KafkaListener(topics = "requestTopic", groupId = "group_id")
    public String listen(@Payload String request, @Header(KafkaHeaders.REPLY_TOPIC) String replyTopic) {
        // Выполняем запрос к БД
        String reply = processRequest(request);

        // Отправляем ответ на указанный топик
        return reply;
    }

    private String processRequest(String request) {
        // Здесь должна быть логика для выполнения запроса к БД
        // Например, вы можете использовать репозиторий для выполнения запроса
        return new String(); // Верните ответ
    }
}
