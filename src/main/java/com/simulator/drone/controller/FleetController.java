package com.simulator.drone.controller;

import com.simulator.drone.model.FleetConfig;
import com.simulator.drone.service.DroneSimulationService;
import com.simulator.drone.service.ShipSimulationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fleet")
public class FleetController {

    private final ShipSimulationService  shipService;
    private final DroneSimulationService droneService;

    public FleetController(ShipSimulationService shipService,
                           DroneSimulationService droneService) {
        this.shipService  = shipService;
        this.droneService = droneService;
    }

    /**
     * GET /api/fleet/config — return the current fleet configuration.
     */
    @GetMapping("/config")
    public FleetConfig getConfig() {
        return new FleetConfig(
                shipService.getCurrentShipCount(),
                droneService.getCurrentDronesPerShip()
        );
    }

    /**
     * PUT /api/fleet/config — resize the fleet at runtime.
     * Validation: shipCount 1–100, dronesPerShip 1–1000.
     */
    @PutMapping("/config")
    public ResponseEntity<?> putConfig(@RequestBody FleetConfig config) {
        int ships  = config.getShipCount();
        int drones = config.getDronesPerShip();

        if (ships < 1 || ships > 100) {
            return ResponseEntity.badRequest()
                    .body("shipCount must be between 1 and 100");
        }
        if (drones < 1 || drones > 1000) {
            return ResponseEntity.badRequest()
                    .body("dronesPerShip must be between 1 and 1000");
        }

        shipService.resize(ships);
        droneService.resize(ships, drones);

        return ResponseEntity.ok(new FleetConfig(
                shipService.getCurrentShipCount(),
                droneService.getCurrentDronesPerShip()
        ));
    }
}
