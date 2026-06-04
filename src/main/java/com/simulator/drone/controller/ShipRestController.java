package com.simulator.drone.controller;

import com.simulator.drone.model.ShipPayload;
import com.simulator.drone.service.ShipSimulationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/ships")
public class ShipRestController {

    private final ShipSimulationService simulationService;

    public ShipRestController(ShipSimulationService simulationService) {
        this.simulationService = simulationService;
    }

    @GetMapping
    public Collection<ShipPayload> getAll() {
        return simulationService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShipPayload> getById(@PathVariable String id) {
        return simulationService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
