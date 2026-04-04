CREATE TABLE users(
     id BIGSERIAL PRIMARY KEY,
     email VARCHAR(250) NOT NULL UNIQUE,
     password VARCHAR(250) NOT NULL,
     full_name VARCHAR(55) NOT NULL,
     role VARCHAR(15) NOT NULL,
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE account(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    currency VARCHAR(50),
    balance NUMERIC(15,2) NOT NULL DEFAULT 0,
    version BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_account_user_id FOREIGN KEY (user_id) REFERENCES users(id)
);
CREATE TABLE transactions(
    id BIGSERIAL PRIMARY KEY,
    from_account_id BIGINT NOT NULL,
    to_account_id BIGINT NOT NULL,
    amount NUMERIC(15,2),
    type VARCHAR(50),
    status VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_from_account_id FOREIGN KEY (from_account_id) REFERENCES account(id),
    CONSTRAINT fk_to_account_id FOREIGN KEY (to_account_id) REFERENCES account(id)
);
