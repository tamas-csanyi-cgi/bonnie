package com.cgi.hexagon.commplugin.kafka;

import com.cgi.hexagon.communicationplugin.MessageService;
import com.cgi.hexagon.communicationplugin.SendRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class KafkaMessageService implements MessageService {

    private final JsonMapper jsonMapper= new JsonMapper();
    @Autowired
    final private KafkaTemplate<String, String> kafkaTemplate;
    @Value("${spring.bonnie.kafka.topic.message}")
    private String topicName;

    public KafkaMessageService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostConstruct
    public void postConstruct() {
        log.info("Message producer for topic [{}] running.", topicName);
    }

    @Override
    public void send(SendRequest request) {
        kafkaTemplate.send(topicName, jsonMapper.write(request));
        log.debug("[{}] topic a message sent: {}", topicName, request);
    }
}
