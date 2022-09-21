package com.cgi.hexagon.commplugin.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Slf4j
@Configuration
public class TopicConfig {
    @Value("${spring.bonnie.kafka.topic.order}")
    private String topicOrders;
    @Value("${spring.bonnie.kafka.topic.message}")
    private String topicMessage;

    @Bean
    public NewTopic bonnieOrderTopic() {
        log.info(" [{}}] topic is creating.", topicOrders);
        return TopicBuilder.name(topicOrders).build();
    }

    @Bean
    public NewTopic bonnieMessageTopic() {
        log.info(" [{}}] topic is creating.", topicMessage);
        return TopicBuilder.name(topicMessage).build();
    }
}
