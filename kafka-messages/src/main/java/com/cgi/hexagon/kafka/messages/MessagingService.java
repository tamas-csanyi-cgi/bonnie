package com.cgi.hexagon.kafka.messages;

import com.cgi.hexagon.businessrules.SendRequest;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class MessagingService {

    KafkaProducer kp;

    public MessagingService() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        /*properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kp = new KafkaProducer(properties);*/
        KafkaJSON handler = new KafkaJSON(SendRequest.class);
        kp = new KafkaProducer(properties, handler, handler);
    }

    public boolean sendMessage(String topic, SendRequest request) {
        try {
            ProducerRecord pr = new ProducerRecord(topic, request);
            kp.send(pr);
            return true;
        }catch (Exception e) {}
        return false;
    }
}
