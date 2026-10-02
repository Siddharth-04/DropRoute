package com.droproute.backend.route;

import com.droproute.backend.auth.User;
import com.droproute.backend.auth.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/routes")
public class RouteController {

    private final RouteService routeService;
    private final UserRepository userRepository;

    public RouteController(RouteService routeService, UserRepository userRepository){
        this.routeService = routeService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<RouteResponse> createRoute(
            @Valid @RequestBody CreateRouteRequest request,
            Authentication authentication
    ){

        UUID userId = (UUID) authentication.getPrincipal();

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found")
                );

        Route route = routeService.createRoute(
                request.getFileId(),
                request.getExpiresAt(),
                user
        );

        RouteResponse response = new RouteResponse(
                route.getId(),
                route.getFile().getId(),
                route.getSecureToken(),
                "http://localhost:8080/d/" + route.getSecureToken(),
                route.getStatus(),
                route.getCreatedAt(),
                route.getExpiresAt(),
                route.getRevokedAt()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}