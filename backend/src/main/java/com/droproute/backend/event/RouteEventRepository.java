package com.droproute.backend.event;

import com.droproute.backend.route.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface RouteEventRepository extends JpaRepository<RouteEvent, UUID> {
    List<RouteEvent> findAllByRouteOrderByTimestampDesc(Route route);
    List<RouteEvent> findAllByRouteIdOrderByTimestampDesc(UUID routeId);
}
