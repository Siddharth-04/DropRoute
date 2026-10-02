package com.droproute.backend.route;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface RouteRepository extends JpaRepository<Route, UUID> {
    @EntityGraph(attributePaths = "file")
    Optional<Route> findBySecureToken(String secureToken);
    boolean existsBySecureToken(String secureToken);
}
