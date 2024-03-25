-- Creat table persons
CREATE TABLE IF NOT EXISTS persons (
    person_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    genre VARCHAR(255),
    age INT,
    identification VARCHAR(255),
    address VARCHAR(255),
    phone VARCHAR(255)
);

-- Create table clients
CREATE TABLE IF NOT EXISTS clients (
    client_id BIGINT,
    password VARCHAR(255) NOT NULL,
    status BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (client_id) REFERENCES persons(person_id)
);

/*INSERT INTO persons (name, address, phone) VALUES ('Jose Lema', 'Otavalo sn y principal', '098254785');
INSERT INTO persons (name, address, phone) VALUES ('Marianela Montalvo', 'Amazonas y NNUU', '097548965');
INSERT INTO persons (name, address, phone) VALUES ('Juan Osorio', '13 junio y Equinoccial', '098874587');

INSERT INTO clients (client_id, password) VALUES (1, '1234');
INSERT INTO clients (client_id, password) VALUES (2, '5678');
INSERT INTO clients (client_id, password) VALUES (3, '1245');*/