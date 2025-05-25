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
}

