package com.droproute.backend.file;

import java.time.LocalDateTime;
import java.util.UUID;

public class FileResponse {

    private UUID id;
    private String originalName;
    private Long size;
    private String contentType;
    private String checksum;
    private LocalDateTime createdAt;

    public FileResponse(
            UUID id,
            String originalName,
            Long size,
            String contentType,
            String checksum,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.originalName = originalName;
        this.size = size;
        this.contentType = contentType;
        this.checksum = checksum;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public Long getSize() {
        return size;
    }

    public String getContentType() {
        return contentType;
    }

    public String getChecksum() {
        return checksum;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}