package com.soluix_learn.irawan.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class KafkaProducer {

    private final static String TOPIC ="soluixer"; // Konstanta untuk nama topik Kafka

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate; // injeksi dependensi kafktaTemplate

    public String sendMessage(String message){
        // Mengirim pesan ke topik dan mendapatkan CompletableFuture
        CompletableFuture<SendResult<String ,String>> future =kafkaTemplate.send(TOPIC,message);

        try {
            // Menunggu hasil pengiriman pesan
           SendResult<String, String> result =future.get();

            // Mengembalikan string yang berisi informasi pengiriman yang sukses
            return String.format("Message sent successfully to topic %s, partition %d, offset %d",
                    result.getRecordMetadata().topic(),
                    result.getRecordMetadata().partition(),
                    result.getRecordMetadata().offset());

        }catch (Exception e){
            return "failed to send Message: "+e.getMessage();
        }
    }
}
