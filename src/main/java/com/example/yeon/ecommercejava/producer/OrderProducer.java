package com.example.yeon.ecommercejava.producer;

import com.example.yeon.ecommercejava.events.OrderEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public OrderProducer(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderEvent(OrderEvent orderEvent){
        kafkaTemplate.send("orders",String.valueOf(orderEvent.getId()), orderEvent);
    }
}
