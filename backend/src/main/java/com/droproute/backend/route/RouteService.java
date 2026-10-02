package com.droproute.backend.route;

import com.droproute.backend.auth.User;
import com.droproute.backend.file.File;
import com.droproute.backend.file.FileService;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@Service
public class RouteService {
    private final RouteRepository routeRepository;
    private final FileService fileService;

    private final SecureRandom secureRandom = new SecureRandom();

    public RouteService(RouteRepository routeRepository, FileService fileService){
        this.routeRepository = routeRepository;
        this.fileService = fileService;
    }

    public Route createRoute(
            UUID fileId,
            LocalDateTime expiresAt,
            User owner
    ){

        File file = fileService.getFileById(fileId, owner);

        String secureToken = generateSecureToken();

        Route route = new Route();

        route.setFile(file);
        route.setSecureToken(secureToken);
        route.setStatus(RouteStatus.ACTIVE);
        route.setCreatedAt(LocalDateTime.now());
        route.setExpiresAt(expiresAt);

        return routeRepository.save(route);
    }

    private String generateSecureToken() {

        byte[] randomBytes = new byte[32];

        secureRandom.nextBytes(randomBytes);

        String token = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);

        while (routeRepository.existsBySecureToken(token)) {
            secureRandom.nextBytes(randomBytes);

            token = Base64.getUrlEncoder()
                    .withoutPadding()
                    .encodeToString(randomBytes);
        }

        return token;
    }

    public Route getActiveRouteByToken(String token) {

        Route route = routeRepository.findBySecureToken(token)
                .orElseThrow(() ->
                        new IllegalArgumentException("Route not found")
                );

        if (route.getStatus() != RouteStatus.ACTIVE) {
            throw new IllegalArgumentException("Route is not active");
        }

        if (route.getExpiresAt() != null &&
                route.getExpiresAt().isBefore(LocalDateTime.now())) {

            route.setStatus(RouteStatus.EXPIRED);
            routeRepository.save(route);

            throw new IllegalArgumentException("Route has expired");
        }

        return route;
    }
}