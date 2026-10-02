package com.droproute.backend.file;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import org.springframework.core.io.Resource;

public interface FileStorageService {
    String store(MultipartFile file) throws IOException;
    void delete(String storagePath) throws IOException;
    Resource load(String storagePath);
}
