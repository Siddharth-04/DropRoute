CREATE TABLE route_events (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    route_id UUID NOT NULL,
    event_type VARCHAR(30) NOT NULL,
    ip_address VARCHAR(45),
    user_agent TEXT,
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_route_events_route
        FOREIGN KEY (route_id)
        REFERENCES routes(id)
        ON DELETE CASCADE,

    CONSTRAINT chk_route_events_type
        CHECK (
            event_type IN (
                'ROUTE_CREATED',
                'DOWNLOAD_STARTED',
                'DOWNLOAD_SUCCESS',
                'DOWNLOAD_FAILED',
                'ROUTE_REVOKED',
                'ROUTE_EXPIRED'
            )
        )
);

CREATE INDEX idx_route_events_route_id
    ON route_events(route_id);

CREATE INDEX idx_route_events_timestamp
    ON route_events(timestamp);