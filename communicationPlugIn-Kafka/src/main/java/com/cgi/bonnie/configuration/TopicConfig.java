package com.cgi.bonnie.configuration;

import com.fasterxml.jackson.databind.JsonSerializer;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

@Configuration
@ComponentScan({"com.cgi.bonnie.commplugin.kafka"})
public class TopicConfig {

    private final Logger log = LoggerFactory.getLogger(TopicConfig.class.getName());

    @Value("${spring.bonnie.kafka.topic.order}")
    private String topicOrders;
    @Value("${spring.bonnie.kafka.topic.message}")
    private String topicMessage;

    @Bean
    public NewTopic bonnieOrderTopic() {
        log.info(" [{}] topic is creating.", topicOrders);
        return TopicBuilder.name(topicOrders).build();
    }
    @Bean
    public NewTopic bonnieMessageTopic() {
        log.debug(" [{}] topic is creating.", topicMessage);
        return TopicBuilder.name(topicMessage).build();
    }

    @Bean
    public KafkaTemplate senderProps(KafkaProperties kafkaProperties) {
        Map<String, Object> props = kafkaProperties.buildProducerProperties();

        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return new KafkaTemplate(new DefaultKafkaProducerFactory<>(props));
    }

}
