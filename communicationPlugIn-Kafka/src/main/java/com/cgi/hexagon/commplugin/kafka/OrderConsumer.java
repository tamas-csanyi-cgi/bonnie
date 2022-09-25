package com.cgi.hexagon.commplugin.kafka;

import com.cgi.hexagon.businessrules.order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderConsumer {

    private OrderService orderService;

    @Value("${spring.bonnie.kafka.topic.order}")
    private String topicName;
    @Value("${spring.kafka.consumer.group-id}")
    private String groupID;

    @Autowired
    public OrderConsumer(OrderService orderService) {
        this.orderService = orderService;
        //    log.info("Kafka consumer is started for Group: [{}] and  Topic: [{}]", groupID, topicName);
    }

    @PostConstruct
    public void postConstruct() {
        log.info("Kafka consumer is started for Group: [{}] and  Topic: [{}]", groupID, topicName);
    }

    @KafkaListener(topics = "${spring.bonnie.kafka.topic.order}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String message) {
        log.info("Message received in Group: [{}] Topic: [{}] Message: [{}]", groupID, topicName, message);
        List<OrderJson> jsonOrders;
        if (message.trim().startsWith("[")) //Json array
            jsonOrders = new JsonMapper().readAll(message);
        else
            jsonOrders = Collections.singletonList(new JsonMapper().read(message));
        jsonOrders = jsonOrders.stream().filter(Objects::nonNull).collect(Collectors.toList());

        if (jsonOrders.size() > 0)
            orderService.createOrders(new OrderMapper().fromOrderJsonList(jsonOrders));
    }
}
