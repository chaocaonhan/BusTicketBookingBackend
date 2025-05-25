package com.example.BusTicketBookingBackend.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
public class OpenRouteService {

    private final String apiKey = "5b3ce3597851110001cf6248735e1e769de04617b629cc9963c11545";
    private final RestTemplate restTemplate = new RestTemplate();

    public double[] getCoordinates(String placeName) {
        String url = "https://api.openrouteservice.org/geocode/search?api_key=" + apiKey + "&text=" + placeName;

        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            List<Map> features = (List<Map>) response.getBody().get("features");
            if (features != null && !features.isEmpty()) {
                Map geometry = (Map) features.get(0).get("geometry");
                if (geometry != null && geometry.get("coordinates") != null) {
                    List<Double> coords = (List<Double>) geometry.get("coordinates");
                    return new double[]{coords.get(0), coords.get(1)};
                }
            }
        }

        throw new RuntimeException("Không tìm được tọa độ cho địa điểm: " + placeName);
    }


    public double getDistanceInKm(String originPlace, String destPlace) {
        double[] from = getCoordinates(originPlace);
        double[] to = getCoordinates(destPlace);

        String url = "https://api.openrouteservice.org/v2/matrix/driving-car";

        Map<String, Object> body = new HashMap<>();
        body.put("locations", Arrays.asList(from, to));
        body.put("metrics", Arrays.asList("distance"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", apiKey);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            List<List<Double>> distances = (List<List<Double>>) response.getBody().get("distances");

            if (distances != null && !distances.isEmpty()
                    && distances.get(0) != null
                    && distances.get(0).size() > 1
                    && distances.get(0).get(1) != null) {

                return Math.round(distances.get(0).get(1) / 1000.0); // mét → km
            }
        }

        throw new RuntimeException("Không tính được khoảng cách giữa 2 địa điểm: " + originPlace + " và " + destPlace);
    }

}
