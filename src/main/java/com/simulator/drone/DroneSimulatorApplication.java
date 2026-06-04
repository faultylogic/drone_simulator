package com.simulator.drone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DroneSimulatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(DroneSimulatorApplication.class, args);
    }
}
