package com.soluix_learn.irawan.controller;

import com.soluix_learn.irawan.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
public class KafkaController {
    @Autowired
    private KafkaProducer producer;

    @PostMapping("/publishMessage") // menambahkan respon yang lebih menarik dan mengirimkan data lewat body
    public ResponseEntity<Map<String, String>> publishMessage(@RequestBody Map<String, String> body){
        String message = body.get("message"); // mengambil data dari body dengan key message
        // penjagaan jika pesan kosong
        if (message == null || message.isEmpty()){
            return ResponseEntity.badRequest().body(Map.of("status","error", "message","Message Cannot be Empty"));
        }
        String result = producer.sendMessage(message); // // Mengirim pesan ke Kafka menggunakan producer

        return ResponseEntity.ok(Map.of("status", "succes", "message", result));
    }
}
