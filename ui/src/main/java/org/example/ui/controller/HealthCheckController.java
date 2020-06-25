package org.example.ui.controller;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import javax.enterprise.context.ApplicationScoped;

@Liveness
@ApplicationScoped
public class HealthCheckController implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.named("ui service").up().build();
    }

}
