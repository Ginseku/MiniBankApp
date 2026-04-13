ALTER TABLE users ADD COLUMN is_locked BOOLEAN DEFAULT FALSE;

CREATE TABLE api_log (
    id BIGSERIAL PRIMARY KEY,
    from_account_id BIGINT,
    to_account_id BIGINT,
    amount NUMERIC(15, 2),
    status VARCHAR(50),
    message TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);