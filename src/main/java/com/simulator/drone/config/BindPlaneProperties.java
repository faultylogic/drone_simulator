package com.simulator.drone.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bindplane")
public class BindPlaneProperties {

    private boolean enabled = true;
    private String serviceName = "drone-simulator";

    /** gRPC endpoint — used for metrics (BindPlane gRPC port, default 4318) */
    private String grpcEndpoint = "http://localhost:4318";

    /** HTTP endpoint — used for logs (BindPlane HTTP port, default 4317) */
    private String httpEndpoint = "http://localhost:4317";

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }

    public String getGrpcEndpoint() { return grpcEndpoint; }
    public void setGrpcEndpoint(String grpcEndpoint) { this.grpcEndpoint = grpcEndpoint; }

    public String getHttpEndpoint() { return httpEndpoint; }
    public void setHttpEndpoint(String httpEndpoint) { this.httpEndpoint = httpEndpoint; }
}
