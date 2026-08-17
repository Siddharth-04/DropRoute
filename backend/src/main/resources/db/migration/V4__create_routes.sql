CREATE TABLE routes(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid() ,
    file_id UUID NOT NULL ,
    secure_token VARCHAR(128) NOT NULL ,
    status VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP ,
    revoked_at TIMESTAMP ,
    CONSTRAINT uk_routes_secure_token UNIQUE (secure_token) ,
    CONSTRAINT fk_routes_file
        FOREIGN KEY (file_id)
        REFERENCES files(id)
        ON DELETE CASCADE ,
    CONSTRAINT chk_routes_status
        CHECK (status IN ('DRAFT', 'ACTIVE', 'EXPIRED', 'REVOKED'))
);

CREATE INDEX idx_routes_status
    ON routes(status);