package com.droproute.backend.file;

import com.droproute.backend.auth.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {

    private final FileRepository fileRepository;
    private final FileStorageService fileStorageService;

    public FileService(
            FileRepository fileRepository,
            FileStorageService fileStorageService
    ) {
        this.fileRepository = fileRepository;
        this.fileStorageService = fileStorageService;
    }

    public File upload(User owner, MultipartFile uploadedFile)
            throws IOException {

        String storagePath = fileStorageService.store(uploadedFile);

        File file = new File();

        file.setOwner(owner);
        file.setOriginalName(uploadedFile.getOriginalFilename());
        file.setStoragePath(storagePath);
        file.setSize(uploadedFile.getSize());
        file.setContentType(uploadedFile.getContentType());
        file.setChecksum(calculateChecksum(uploadedFile));

        return fileRepository.save(file);
    }

    private String calculateChecksum(MultipartFile file)
            throws IOException {

        try {
            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");

            try (InputStream inputStream = file.getInputStream()) {

                byte[] buffer = new byte[8192];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    digest.update(buffer, 0, bytesRead);
                }
            }

            byte[] hash = digest.digest();

            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                hexString.append(
                        String.format("%02x", b)
                );
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                    "SHA-256 algorithm is not available",
                    e
            );
        }
    }

    public List<File> getFilesByOwnerId(UUID ownerId) {
        return fileRepository.findAllByOwnerId(ownerId);
    }

    public File getFileById(UUID fileId, User owner) {

        return fileRepository.findById(fileId)
                .filter(file -> file.getOwner().getId().equals(owner.getId()))
                .orElseThrow(() ->
                        new IllegalArgumentException("File not found")
                );
    }
}