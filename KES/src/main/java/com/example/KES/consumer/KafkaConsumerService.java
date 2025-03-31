package com.example.KES.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "orders_topic", groupId = "kes_group")
    public void consumeOrder(String orderJson) {
        System.out.println("📥 Otrzymano zamówienie z Kafka: " + orderJson);
    }

    @KafkaListener(topics = "address_topic", groupId = "kes_group")
    public void consumeAddressChange(String addressJson) {
        System.out.println("📥 Otrzymano zmianę adresu z Kafka: " + addressJson);
    }
}