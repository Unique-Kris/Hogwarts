CREATE TABLE Person (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    age INT CHECK (age >= 16),
    has_license BOOLEAN DEFAULT FALSE
);

CREATE TABLE Car (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    brand VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE Person_Car (
    person_id BIGINT,
    car_id BIGINT,
    PRIMARY KEY (person_id, car_id),
    FOREIGN KEY (person_id) REFERENCES Person(id),
    FOREIGN KEY (car_id) REFERENCES Car(id)
);