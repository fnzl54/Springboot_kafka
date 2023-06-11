package com.kafka.demo.service;

import com.kafka.demo.service.WeatherService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.kafka.demo.service.KafkaProducer;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class WeatherScheduled {

  private final KafkaProducer producer;
  private final WeatherService weatherService;

  @Scheduled(fixedDelay = 10000) //3시간마다 실행 (3*60*60*1000 = 10800000)
  public void updateWeather() {
    System.out.println("날씨 업데이트 진행 중 !!"); // 이후 log로 수정 할 예정

    List<String> weatherData = new ArrayList<>();
    try {
      weatherData = weatherService.getWeatherData("seoul");
    } catch (Exception e) {
      System.out.println("날씨 데이터를 가져오는 중에 오류가 발생했습니다.");
    }

    for (String weatherDataForTime : weatherData) {
      String[] regionalTimeWeather = weatherDataForTime.split("/");
      this.producer.sendMessage(regionalTimeWeather[0], regionalTimeWeather[1]);
    }
  }
}
