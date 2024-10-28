package com.soluix_learn.irawan.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "soluixer", groupId = "group-id") // Anotasi untuk mendefinisikan method ini sebagai Kafka listener
    public void listen(String message){
        System.out.println("Received Message : "+ message);
    } // mencetak diconsole setiap mengirim pesan dari publish ke consumer
}
