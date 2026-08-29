package com.droproute.backend.file;

import com.droproute.backend.auth.User;
import com.droproute.backend.auth.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/files")
public class FileController {

    private final FileService fileService;
    private final UserRepository userRepository;

    public FileController(
            FileService fileService,
            UserRepository userRepository
    ) {
        this.fileService = fileService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<FileResponse> upload(
            @RequestParam("file") MultipartFile uploadedFile,
            Authentication authentication
    ) throws IOException {

        UUID userId = (UUID) authentication.getPrincipal();

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found")
                );

        File savedFile =
                fileService.upload(user, uploadedFile);

        FileResponse response = new FileResponse(
                savedFile.getId(),
                savedFile.getOriginalName(),
                savedFile.getSize(),
                savedFile.getContentType(),
                savedFile.getChecksum(),
                savedFile.getCreatedAt()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}