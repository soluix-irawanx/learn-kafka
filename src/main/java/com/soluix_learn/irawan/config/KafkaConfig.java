package com.soluix_learn.irawan.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    // konfigurasi untuk producer/publisher
    @Bean
    public ProducerFactory<String, String> producerFactory(){
        Map<String, Object> producer = new HashMap<>();
        producer.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers); // Menentukan alamat bootstrap server Kafka
        producer.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // Menentukan serializer untuk key (menggunakan StringSerializer)
        producer.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // Menentukan serializer untuk value (menggunakan StringSerializer)

        return new DefaultKafkaProducerFactory<>(producer); // // Membuat dan mengembalikan DefaultKafkaProducerFactory dengan konfigurasi yang telah ditentukan
    }

    @Bean
    public KafkaTemplate<String, String > kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }

    // configurasi untuk consumer
    @Bean
    public ConsumerFactory<String, String> consumerFactory(){
        Map<String ,Object> consumer = new HashMap<>();
        consumer.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        consumer.put(ConsumerConfig.GROUP_ID_CONFIG, "group-id"); // Menentukan ID grup untuk consumer
        consumer.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // Menentukan deserializer untuk key (menggunakan StringDeserializer)
        consumer.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // Menentukan deserializer untuk value (menggunakan StringDeserializer)
        return new DefaultKafkaConsumerFactory<>(consumer);
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>(); // Membuat instance ConcurrentKafkaListenerContainerFactory
        factory.setConsumerFactory(consumerFactory());
        return factory; // Mengembalikan factory yang telah dikonfigurasi
    }

}
