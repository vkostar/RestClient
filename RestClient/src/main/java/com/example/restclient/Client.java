package com.example.restclient;

import com.example.restclient.dto.MeasurementResponse;
import com.example.restclient.dto.MeasurementsDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class Client {
    public static void main(String[] args) {
        String nameOfSensor = "name1";
//        registerSensor(nameOfSensor);
//        addMeasurements(nameOfSensor);

        System.out.println(getMeasurements());
    }

    public static void registerSensor(String sensor) {

        String url = "http://localhost:8080/sensors/registration";
        Map<String, Object> json = new HashMap<>();
        json.put("nameOfSensor", sensor);

        makePostRequest(url, json);
    }

    public static void addMeasurements(String sensor) {

        String url = "http://localhost:8080/measurements/add";
        Random random = new Random();
        int maxTemp = 50;
        for (int i = 0; i < 1000; i++) {

            Double value = random.nextDouble() * maxTemp;
            Boolean raining = random.nextBoolean();
            Map<String, Object> json = new HashMap<>();
            json.put("value", value);
            json.put("raining", raining);
            json.put("sensor", Map.of("nameOfSensor", sensor));
            makePostRequest(url, json);
        }

    }

    private static void makePostRequest(String url, Map<String, Object> json) {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> request = new HttpEntity<>(json, headers);
        try {
            restTemplate.postForEntity(url, request, String.class);
            System.out.println("Измерение отправлено на сервер");
        } catch (HttpClientErrorException e) {
            System.out.println(e.getMessage());
        }

    }


    private static List<Double> getMeasurements() {

        String url = "http://localhost:8080/measurements";
        final RestTemplate restTemplate = new RestTemplate();

        MeasurementResponse response = restTemplate.getForObject(url, MeasurementResponse.class);

        assert response != null;
        return response.getMeasurementsDTOList().stream().map(MeasurementsDTO::getValue).collect(Collectors.toList());
    }


}
