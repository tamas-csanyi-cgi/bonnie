package com.cgi.hexagon.kafka.messages;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class MessagingService {

    KafkaProducer kp;

    public MessagingService() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kp = new KafkaProducer(properties);
    }

    public boolean sendMessage(String topic, String value) {
        try {
            ProducerRecord pr = new ProducerRecord(topic, value);
            kp.send(pr);
            return true;
        }catch (Exception e) {}
        return false;
    }
}
