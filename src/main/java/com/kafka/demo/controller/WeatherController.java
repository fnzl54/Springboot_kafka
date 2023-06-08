package com.kafka.demo.controller;

import com.kafka.demo.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class WeatherController {
  private final WeatherService weatherService;

  @GetMapping("/weather/{location}")
  public String getWeather(@PathVariable String location) {
    try {
      String weatherData = weatherService.getWeatherData(location);
      return weatherData;
    } catch (Exception e) {
      return "날씨 데이터를 가져오는 중에 오류가 발생했습니다.";
    }
  }
}
