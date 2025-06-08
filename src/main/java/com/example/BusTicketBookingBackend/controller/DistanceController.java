package com.example.BusTicketBookingBackend.controller;

import com.example.BusTicketBookingBackend.service.OpenRouteService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/distance")
public class DistanceController {

    private final OpenRouteService openRouteService;

    public DistanceController(OpenRouteService openRouteService) {
        this.openRouteService = openRouteService;
    }

    @GetMapping
    public String getDistance(
            @RequestParam String from,
            @RequestParam String to
    ) {
        double distance = openRouteService.getDistanceInKm(from, to);
        return String.format("Khoảng cách từ '%s' đến '%s' là: %.2f km", from, to, distance);
    }

    @GetMapping("/toaDo")
    public String getToaDo(@RequestParam String tenDiaDiem) {
        double[] coordinates = openRouteService.getCoordinates(tenDiaDiem);
        if (coordinates != null && coordinates.length == 2) {
            // Đảo thứ tự: vĩ độ, kinh độ
            return coordinates[1] + "," + coordinates[0];
        }
        return "";
    }




}

