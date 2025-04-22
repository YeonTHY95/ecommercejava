package com.example.yeon.ecommercejava.consumer;

import com.example.yeon.ecommercejava.events.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {
    private final Logger log = LoggerFactory.getLogger(OrderConsumer.class);

    @KafkaListener(topics = "orders", groupId = "ecommerce-group", containerFactory = "orderKafkaListenerFactory")
    public void consumeOrderEvent(OrderEvent orderEvent) {
        log.info("Received OrderEvent: {}", orderEvent);

        // Process the order here (e.g., update database, send notification, etc.)
    }
}
