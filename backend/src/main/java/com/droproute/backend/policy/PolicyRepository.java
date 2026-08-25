package com.droproute.backend.policy;

import com.droproute.backend.route.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface PolicyRepository extends JpaRepository<Policy, UUID> {
    List<Policy> findAllByRoute(Route route);

    List<Policy> findAllByRouteId(UUID routeId);
}
