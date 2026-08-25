package com.droproute.backend.file;

import com.droproute.backend.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface FileRepository extends JpaRepository<File, UUID> {
    List<File> findAllByOwner(User owner);
    List<File> findAllByOwnerId(UUID ownerId);
}
