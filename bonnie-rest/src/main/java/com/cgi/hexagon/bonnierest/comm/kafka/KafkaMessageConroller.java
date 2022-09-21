package com.cgi.hexagon.bonnierest.comm.kafka;

import com.cgi.hexagon.commplugin.kafka.MessageProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka/msg")
public class KafkaMessageConroller {
    private MessageProducer messageProducer;

    public KafkaMessageConroller(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @GetMapping("/send")
    public ResponseEntity<String> publish(@RequestBody String message) {
        messageProducer.sendMessage(message);
        return ResponseEntity.ok("Message sent to kafka topic");
    }
}
