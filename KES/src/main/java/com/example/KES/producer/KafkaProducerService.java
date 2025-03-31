package com.example.KES.producer;

import com.example.KES.dto.AddressDTO;
import com.example.KES.dto.OrderDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrder(OrderDTO order) {
        kafkaTemplate.send("orders_topic", order.toString());
        System.out.println("✅ Wysłano zamówienie do Kafka: " + order);
    }

    public void sendAddressChange(AddressDTO address) {
        kafkaTemplate.send("address_topic", address.toString());
        System.out.println("✅ Wysłano zmianę adresu do Kafka: " + address);
    }
}