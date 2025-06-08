package com.example.BusTicketBookingBackend.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class OpenRouteService {

    private final String apiKey = "5b3ce3597851110001cf6248735e1e769de04617b629cc9963c11545";
    private final RestTemplate restTemplate = new RestTemplate();

    public double[] getCoordinates(String placeName) {
        String url = UriComponentsBuilder
                .fromHttpUrl("https://nominatim.openstreetmap.org/search")
                .queryParam("format", "json")
                .queryParam("limit", "1")
                .queryParam("q", placeName)
                .build()
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "MySpringApp");

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<List> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                List.class
        );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null && !response.getBody().isEmpty()) {
            Map result = (Map) response.getBody().get(0);
            double lat = Double.parseDouble((String) result.get("lat"));
            double lon = Double.parseDouble((String) result.get("lon"));
            return new double[]{lon, lat};
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
