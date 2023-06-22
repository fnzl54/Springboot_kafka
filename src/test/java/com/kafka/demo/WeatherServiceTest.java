package com.kafka.demo;

import com.kafka.demo.service.WeatherService;
import static org.assertj.core.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;

public class WeatherServiceTest {

  @Test
  void getLatLonDataTest() throws Exception {
    //given
    final WeatherService getLatLonData = new WeatherService();
    final String location = "서울";

    //when
    final List<Double> LatLonData = getLatLonData.getLatLonData(location);

    //then
    assertThat(LatLonData.get(0)).isEqualTo(37.5666791);
    assertThat(LatLonData.get(1)).isEqualTo(126.9782914);
  }
}
