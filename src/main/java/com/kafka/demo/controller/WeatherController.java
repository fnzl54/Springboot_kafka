package com.kafka.demo.controller;

import com.kafka.demo.service.KafkaProducer;
import com.kafka.demo.service.WeatherService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class WeatherController {
  private final KafkaProducer producer;
  private final WeatherService weatherService;

  @GetMapping("/weather1/{location}")
  public void getWeatherWithKafka(@PathVariable String location) {
    List<String> weatherData = new ArrayList<>();

    try {
      weatherData = weatherService.getWeatherData(location);
    } catch (Exception e) {
      System.out.println("날씨 데이터를 가져오는 중에 오류가 발생했습니다.");
    }

    for (String weatherDataForTime : weatherData) {
      String[] regionalTimeWeather = weatherDataForTime.split("/");
      this.producer.sendMessage(regionalTimeWeather[0], regionalTimeWeather[1]);
    }
  }

  @GetMapping("/weather2/{location}")
  public void getWeatherWithoutKafka(@PathVariable String location) {
    List<String> weatherData = new ArrayList<>();

    try {
      weatherData = weatherService.getWeatherData(location);
    } catch (Exception e) {
      System.out.println("날씨 데이터를 가져오는 중에 오류가 발생했습니다.");
    }

    for (String weatherDataForTime : weatherData) {
      String[] regionalTimeWeather = weatherDataForTime.split("/");
      if (regionalTimeWeather[0].equals("Rain")) {
        System.out.println("Consumed message : " + regionalTimeWeather[1] );
      }
    }

  }

}
