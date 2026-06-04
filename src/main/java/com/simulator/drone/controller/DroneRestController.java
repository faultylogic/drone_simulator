package com.simulator.drone.controller;

import com.simulator.drone.model.DronePayload;
import com.simulator.drone.service.DroneSimulationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/drones")
public class DroneRestController {

    private final DroneSimulationService simulationService;

    public DroneRestController(DroneSimulationService simulationService) {
        this.simulationService = simulationService;
    }

    @GetMapping
    public Collection<DronePayload> getAll() {
        return simulationService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DronePayload> getById(@PathVariable String id) {
        return simulationService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
