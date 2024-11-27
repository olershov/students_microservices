package com.example.user.config;

import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    private final String bootstrapServers;
    private final String groupId;
    private final String replyTopic;

    public KafkaConfig(@Value("${spring.kafka.producer.bootstrap-servers}") String bootstrapServers,
                       @Value("${spring.kafka.consumer.group-id}") String groupId,
                       @Value("${spring.kafka.reply.topic}") String replyTopic) {
        this.bootstrapServers = bootstrapServers;
        this.groupId = groupId;
        this.replyTopic = replyTopic;
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public KafkaMessageListenerContainer<String, String> replyContainer() {
        ContainerProperties containerProps = new ContainerProperties(replyTopic); // Укажите топик для ответов
        return new KafkaMessageListenerContainer<>(consumerFactory(), containerProps);
    }

    @Bean
    public ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate() {
        return new ReplyingKafkaTemplate<>(producerFactory(), replyContainer());
    }
}
