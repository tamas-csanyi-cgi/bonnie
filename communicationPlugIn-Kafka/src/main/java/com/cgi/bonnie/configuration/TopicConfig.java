package com.cgi.bonnie.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

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
}
