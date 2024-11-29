package com.example.user.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@RequiredArgsConstructor
@Service
public class KafkaSenderService {

    private static final Logger LOGGER = Logger.getLogger(KafkaSenderService.class.getName());
    private final ReplyingKafkaTemplate<String, String, String> kafkaTemplate;
    @Value("${spring.kafka.request.topic}")
    private String requestTopic;

    @SneakyThrows
    public String sendSyncRequest(String request) {
        ProducerRecord<String, String> record = new ProducerRecord<>(requestTopic, request);
        LOGGER.info("Sending request: " + request);
        RequestReplyFuture<String, String, String> replyFuture = kafkaTemplate.sendAndReceive(record);
        ConsumerRecord<String, String> consumerRecord = replyFuture.get();
        LOGGER.info("Received reply: " + consumerRecord.value());
        return consumerRecord.value();
    }
}

