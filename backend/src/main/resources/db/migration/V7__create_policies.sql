CREATE TABLE policies (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    route_id UUID NOT NULL,
    policy_type VARCHAR(30) NOT NULL,
    policy_value TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_policies_route
        FOREIGN KEY (route_id)
        REFERENCES routes(id)
            ON DELETE CASCADE,

    CONSTRAINT chk_policies_type
    CHECK (
        policy_type IN (
            'EXPIRY_DATE',
            'DOWNLOAD_LIMIT',
            'PASSWORD',
            'EMAIL_RESTRICTION'
        )
    )
);

CREATE INDEX idx_policies_route_id
    ON policies(route_id);