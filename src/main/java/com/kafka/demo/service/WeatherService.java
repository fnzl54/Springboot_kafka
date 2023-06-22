package com.kafka.demo.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class WeatherService {

  private static final String API_KEY = "79273ab2da4e7dc54b7809e606f91f3e";
  private static final String API_WEATHER_URL = "https://api.openweathermap.org/data/2.5/forecast";
  private static final String API_LOCATION_URL = "http://api.openweathermap.org/geo/1.0/direct";

  public  List<String> getWeatherData(String location) throws Exception {
    /*
    주석의 경우 대규모 kakfa 테스트를 위해 인위적으로 대규모 json를 사용하기 위해 주석처리
     */

    List<Double> latLon = getLatLonData(location);
    List<String> res = new ArrayList<>();

    RestTemplate restTemplate = new RestTemplate();
    String jsonResponse = restTemplate.getForObject(
        API_WEATHER_URL + "?lat=" + latLon.get(0) + "&lon=" + latLon.get(1) + "&appid=" + API_KEY, String.class);

    //Path path = Paths.get("/Users/kwonchan-yeong/Desktop/kafka/src/main/java/com/kafka/demo/test.txt");
    //String jsonResponse = Files.readString(path);

    JSONObject jsonObject = new JSONObject(jsonResponse);
    JSONArray listArray = jsonObject.getJSONArray("list");

    for (int i = 0; i < listArray.length(); i++) {
      JSONObject listItem = listArray.getJSONObject(i);
      String date = listItem.getString("dt_txt");

      JSONArray weatherArray = listItem.getJSONArray("weather");
      for (int j = 0; j < weatherArray.length(); j++) {
        JSONObject weatherItem = weatherArray.getJSONObject(j);
        res.add( weatherItem.getString("main")+ "/"+ date + " : " + location );
      }
    }

    return res;
  }

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
