package com.example.user.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyMessageFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

    private final ReplyingKafkaTemplate<String, String, String> kafkaTemplate;
    private final String replyTopic;

    public KafkaService(ReplyingKafkaTemplate<String, String, String> kafkaTemplate,
                        @Value("${spring.kafka.reply.topic}") String replyTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.replyTopic = replyTopic;
    }

    public String sendRequest(String request) {
        // Создаем сообщение
        var message = MessageBuilder.withPayload(request).setHeader(KafkaHeaders.REPLY_TOPIC, replyTopic).build();
        // Отправляем сообщение и ждем ответа
        RequestReplyMessageFuture<String, String> future = kafkaTemplate.sendAndReceive(message);
        try {
            return (String) future.get().getPayload();
        } catch (Exception e) {
            // Обработка ошибок
            e.printStackTrace();
            return null;
        }
    }
}

