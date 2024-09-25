package com.podgaietska.kafka.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.podgaietska.kafka.services.KafkaProducerService;

@RestController
public class KafkaProducerController {

  private final KafkaProducerService kafkaProducerService;

  public KafkaProducerController(KafkaProducerService kafkaProducerService) {
    this.kafkaProducerService = kafkaProducerService;
  }

  @PostMapping("/send")
  public String sendMessage(@RequestBody String message) {
    kafkaProducerService.send("test-topic", message);
    return "Message sent to kafka topic!";
  }
}
