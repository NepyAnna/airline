SET foreign_key_checks = 0;

DROP TABLE IF EXISTS flights;
DROP TABLE IF EXISTS airports;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS profiles;
DROP TABLE IF EXISTS roles_users;
DROP TABLE IF EXISTS bookings;
CREATE TABLE airports (
    id_airport INT AUTO_INCREMENT PRIMARY KEY,
    name_airport VARCHAR(255) NOT NULL,
    code_iata_airport VARCHAR(3) NOT NULL
);

CREATE TABLE flights (
    id_flight INT AUTO_INCREMENT PRIMARY KEY,
    id_departure_airport INT NOT NULL,
    id_arrival_airport INT NOT NULL,
    date_flight DATETIME NOT NULL,
    status_flight ENUM('AVAILABLE', 'CANCELLED') NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    available_seats INT NOT NULL,
    total_seats INT NOT NULL,
    FOREIGN KEY (id_departure_airport) REFERENCES airports(id_airport),
    FOREIGN KEY (id_arrival_airport) REFERENCES airports(id_airport)
);


CREATE TABLE roles (
    id_role INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);


CREATE TABLE users (
    id_user INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE profiles (
    id_profile INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone_number VARCHAR(20),
    address VARCHAR(255),
    user_id INT NOT NULL,
    photo_url VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(id_user)
);


CREATE TABLE roles_users (
    role_id INT NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY (role_id, user_id),
    FOREIGN KEY (role_id) REFERENCES roles(id_role),
    FOREIGN KEY (user_id) REFERENCES users(id_user)
);


CREATE TABLE bookings (
    id_booking INT AUTO_INCREMENT PRIMARY KEY,
    id_user INT NOT NULL,
    date_booking DATETIME NOT NULL,
    booked_seats INT NOT NULL,
    id_flight INT NOT NULL,
    status ENUM('CONFIRMED', 'CANCELLED') NOT NULL,
    FOREIGN KEY (id_user) REFERENCES users(id_user),
    FOREIGN KEY (id_flight) REFERENCES flights(id_flight)
);
SET foreign_key_checks = 1;