package com.droproute.backend.file;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@Service
public class LocalFileStorageService implements FileStorageService {

    private final Path uploadDirectory =
            Paths.get("uploads").toAbsolutePath().normalize();

    @Override
    public String store(MultipartFile file) throws IOException {

        Files.createDirectories(uploadDirectory);

        String originalFilename = file.getOriginalFilename();

        String extension = "";

        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(
                    originalFilename.lastIndexOf(".")
            );
        }

        String storedFilename = UUID.randomUUID() + extension;

        Path targetLocation =
                uploadDirectory.resolve(storedFilename);

        Files.copy(
                file.getInputStream(),
                targetLocation,
                java.nio.file.StandardCopyOption.REPLACE_EXISTING
        );

        return targetLocation.toString();
    }

    @Override
    public void delete(String storagePath) throws IOException {

        Path filePath = Paths.get(storagePath);

        Files.deleteIfExists(filePath);
    }

    @Override
    public Resource load(String storagePath) {

        Path filePath = Paths.get(storagePath);

        Resource resource = new FileSystemResource(filePath);

        if (!resource.exists() || !resource.isReadable()) {
            throw new IllegalArgumentException("File not found");
        }

        return resource;
    }
}