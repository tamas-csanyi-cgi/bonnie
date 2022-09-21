package com.cgi.hexagon.bonnierest.comm.kafka;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka/msg")
public class KafkaOrderController {
    private OrderProducer orderProducer;

    public KafkaOrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @GetMapping("/sendOrder")
    public ResponseEntity<String> publish(@RequestBody String message) {
        orderProducer.sendOrders(message);
        return ResponseEntity.ok("Orders sent to kafka topic");
    }
}
