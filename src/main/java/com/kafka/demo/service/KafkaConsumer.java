package com.kafka.demo.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KafkaConsumer {

  @KafkaListener(topics = "Rain", groupId = "kafka-demo")
  public void consume(String message) throws IOException {
    long startTime = System.nanoTime();
    System.out.println("Consumed message : " + message );
    long endTime = System.nanoTime();
    long processingTime = endTime - startTime;
    double processingTimeMs = (double) processingTime / 1_000_000;
    System.out.println("메시지 소비 시간: " + processingTimeMs + "ms");
  }
}