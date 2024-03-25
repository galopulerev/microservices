-- Creat table accounts
CREATE TABLE IF NOT EXISTS accounts (
    account_number VARCHAR(255) PRIMARY KEY,
    account_type VARCHAR(255) NOT NULL,
    opening_balance DECIMAL(10, 2) NOT NULL,
    status BOOLEAN DEFAULT TRUE NOT NULL,
    client_id BIGINT NOT NULL,
    client_name VARCHAR(255)
);

-- Create table transactions
CREATE TABLE IF NOT EXISTS transactions (
    transaction_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(255) NOT NULL,
    created_on TIMESTAMP NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    transaction_type VARCHAR(255) NOT NULL,
    balance DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (account_number) REFERENCES accounts(account_number)
);

/*INSERT INTO accounts (account_number, account_type, opening_balance, client_id) VALUES ('478758', 'Ahorros', 2000, 1);
INSERT INTO accounts (account_number, account_type, opening_balance, client_id) VALUES ('225487', 'Corriente', 100, 2);
INSERT INTO accounts (account_number, account_type, opening_balance, client_id) VALUES ('495878', 'Ahorros', 0, 3);
INSERT INTO accounts (account_number, account_type, opening_balance, client_id) VALUES ('496825', 'Ahorros', 540, 2);
INSERT INTO accounts (account_number, account_type, opening_balance, client_id) VALUES ('585545', 'Corriente', 1000, 1);

INSERT INTO transactions (account_number, created_on, amount, transaction_type, balance) VALUES ('478758', '2024-01-30 14:30:00', -575, 'Retiro', 1425);
INSERT INTO transactions (account_number, created_on, amount, transaction_type, balance) VALUES ('225487', '2024-01-30 14:30:00', 600, 'Deposito', 700);
INSERT INTO transactions (account_number, created_on, amount, transaction_type, balance) VALUES ('495878', '2024-01-30 14:30:00', 150, 'Deposito', 150);
INSERT INTO transactions (account_number, created_on, amount, transaction_type, balance) VALUES ('496825', '2024-01-30 14:30:00', -540, 'Retiro', 0);*/
