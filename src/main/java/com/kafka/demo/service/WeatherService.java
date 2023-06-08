package com.kafka.demo.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;

@Service
@RequiredArgsConstructor
public class WeatherService {

  private static final String API_KEY = "";
  private static final String API_WEATHER_URL = "https://api.openweathermap.org/data/2.5/forecast";
  private static final String API_LOCATION_URL = "http://api.openweathermap.org/geo/1.0/direct";


  public List<Double> getLatLonData(String location) throws Exception {

    List<Double> latLon = new ArrayList<>();

    RestTemplate restTemplate = new RestTemplate();
    String jsonResponse = restTemplate.getForObject(
        API_LOCATION_URL + "?q=" + location + "&appid=" + API_KEY, String.class);

    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonArray = objectMapper.readTree(jsonResponse);

    for (JsonNode rootNode : jsonArray) {
      double latitude = rootNode.get("lat").asDouble();
      double longitude = rootNode.get("lon").asDouble();

      latLon.add(latitude);
      latLon.add(longitude);
    }

    return latLon;
  }

}
