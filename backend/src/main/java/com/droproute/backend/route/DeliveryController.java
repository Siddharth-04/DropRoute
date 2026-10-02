package com.droproute.backend.route;

import com.droproute.backend.file.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DeliveryController {

    private final RouteService routeService;
    private final FileStorageService fileStorageService;

    public DeliveryController(
            RouteService routeService,
            FileStorageService fileStorageService
    ) {
        this.routeService = routeService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/d/{token}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable String token
    ) {

        Route route = routeService.getActiveRouteByToken(token);

        Resource resource = fileStorageService.load(
                route.getFile().getStoragePath()
        );

        return ResponseEntity.ok()
                .contentType(
                        MediaType.parseMediaType(
                                route.getFile().getContentType()
                        )
                )
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +
                                route.getFile().getOriginalName() +
                                "\""
                )
                .body(resource);
    }
}