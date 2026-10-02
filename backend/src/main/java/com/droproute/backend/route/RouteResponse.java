package com.droproute.backend.route;

import java.time.LocalDateTime;
import java.util.UUID;

public class RouteResponse {

    private UUID id;
    private UUID fileId;
    private String secureToken;
    private String secureLink;
    private RouteStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime revokedAt;

    public RouteResponse(
            UUID id,
            UUID fileId,
            String secureToken,
            String secureLink,
            RouteStatus status,
            LocalDateTime createdAt,
            LocalDateTime expiresAt,
            LocalDateTime revokedAt
    ) {
        this.id = id;
        this.fileId = fileId;
        this.secureToken = secureToken;
        this.secureLink = secureLink;
        this.status = status;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.revokedAt = revokedAt;
    }

    public UUID getId() {
        return id;
    }

    public UUID getFileId() {
        return fileId;
    }

    public String getSecureToken() {
        return secureToken;
    }

    public String getSecureLink() {
        return secureLink;
    }

    public RouteStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public LocalDateTime getRevokedAt() {
        return revokedAt;
    }
}