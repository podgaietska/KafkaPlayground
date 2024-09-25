package com.podgaietska.kafka.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.confluent.examples.avro.Customer;

import com.podgaietska.kafka.services.KafkaProducerService;

@RestController
public class KafkaProducerController {

  private final KafkaProducerService kafkaProducerService;

  public KafkaProducerController(KafkaProducerService kafkaProducerService) {
    this.kafkaProducerService = kafkaProducerService;
  }

  @PostMapping("/send/customer")
  public String sendOrder(@RequestBody Customer customer) {
    kafkaProducerService.send("events-union", customer);
    return "Customer event sent to kafka topic!";
  }
}
