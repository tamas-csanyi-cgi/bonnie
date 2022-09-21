package com.cgi.hexagon.bonnierest.comm.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderProducer {

    @Autowired
    final private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.bonnie.kafka.topic.order}")
    private String topicName;

    public OrderProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrders(String message) {
        kafkaTemplate.send(topicName, message);
        log.info("[{}] topic a message sent: {}", topicName, message);
    }
}
