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
    status_flight ENUM('AVAILABLE', 'UNAVAILABLE') NOT NULL,
    available_seats INT NOT NULL,
    total_seats INT NOT NULL,
    FOREIGN KEY (id_departure_airport) REFERENCES airports(id_airport),
    FOREIGN KEY (id_arrival_airport) REFERENCES airports(id_airport)
);

CREATE TABLE roles (
    id_role INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE users (
    id_user INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE profiles (
    id_profile INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    user_id INT NOT NULL,
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
    FOREIGN KEY (id_user) REFERENCES users(id_user)
);

CREATE TABLE bookings_flights (
    booking_id INT NOT NULL,
    flight_id INT NOT NULL,
    PRIMARY KEY (booking_id, flight_id),
    FOREIGN KEY (booking_id) REFERENCES bookings(id_booking),
    FOREIGN KEY (flight_id) REFERENCES flights(id_flight)
);
