-- AIRPORTS
INSERT INTO airports (name, code_iata) VALUES
('John F. Kennedy International Airport', 'JFK'),
('Los Angeles International Airport', 'LAX'),
('Chicago O Hare International Airport', 'ORD'),
('Hartsfield-Jackson Atlanta International Airport', 'ATL');

-- FLIGHTS
INSERT INTO flights (id_departure_airport, id_arrival_airport, date_flight, status, price, available_seats, total_seats)
VALUES
(1, 2, '2025-02-12T19:32:00', 'AVAILABLE', 100.50, 150, 150),
(2, 3, '2025-02-15T16:00:00', 'AVAILABLE', 50.75, 120, 150),
(2, 4, '2025-02-11T16:00:00', 'AVAILABLE', 50.75, 120, 150),
(4, 1, '2025-02-11T16:00:00', 'AVAILABLE', 140.75, 120, 120),
(4, 3, '2025-01-30T16:00:00', 'AVAILABLE', 200.75, 120, 120),
(3, 1, '2025-01-30T20:00:00', 'AVAILABLE', 300.00, 0, 100);

-- ROLES
INSERT INTO roles (name) VALUES
('ROLE_USER'),
('ROLE_ADMIN');

-- USERS
INSERT INTO users (username, password) VALUES
('Din', '$2a$12$qanYcTsH3oRXTOPPKW0m9.s.tqyepncgMEDQb9JygZDcq8.Qx1zkG'),
('Don', '$2a$12$qanYcTsH3oRXTOPPKW0m9.s.tqyepncgMEDQb9JygZDcq8.Qx1zkG');

-- PROFILES
INSERT INTO profiles (email, phone_number, address, user_id, photo_url)
VALUES
('din@mail.com', '123456', 'Round street, Square city', 1, 'https://postimg.cc/Y4hcfndB'),
('don@mail.com', '123456', 'Round street, Square city', 2, 'https://postimg.cc/Y4hcfndB');

-- ROLES_USERS
INSERT INTO roles_users (role_id, user_id) VALUES
(1, 1),
(2, 2);

-- BOOKINGS
INSERT INTO bookings (user_id, date_booking, booked_seats, flight_id, status)
VALUES
(1, '2025-01-27T20:00:00', 2, 1, 'CONFIRMED'),
(2, '2025-01-27T20:00:00', 1, 2, 'CONFIRMED');
