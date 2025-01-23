
INSERT INTO airports (id_airport,name_airport,code_iata_airport) VALUES 
    (1, 'John F. Kennedy International Airport', 'JFK'),
    (2, 'Los Angeles International Airport', 'LAX'),
    (3,'Chicago O Hare International Airport', 'ORD'),
    (4, 'Hartsfield-Jackson Atlanta International Airport', 'ATL');

INSERT INTO flights (id_flight, id_departure_airport, id_arrival_airport, date_flight, status_flight, price, available_seats, total_seats) VALUES 
(1, 1, 2, '2025-01-25T10:00:00', 'AVAILABLE', 300.50, 150, 200),
(2, 2, 3, '2025-01-26T15:00:00', 'AVAILABLE', 450.75, 120, 150),
(3, 3, 1, '2025-01-27T20:00:00', 'CANCELLED', 500.00, 0, 100);

INSERT INTO roles (id_role, name) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_ADMIN');

INSERT INTO users (id_user, username, password) VALUES
(1, 'pepe', '$2a$12$8LegtLQWe717tIPvZeivjuqKnaAs5.bm0Q05.5GrAmcKzXw2NjoUO'),
(2, 'pepa', '$2a$12$8LegtLQWe717tIPvZeivjuqKnaAs5.bm0Q05.5GrAmcKzXw2NjoUO');

INSERT INTO profiles (id_profile,email, address, user_id) VALUES 
(1,'pepe@mail.com', 'portal 1',1),
(2,'pepa@mail.com', 'portal 1',2);

INSERT INTO roles_users (role_id, user_id) VALUES 
(1, 1),
(2, 2);

INSERT INTO bookings (id_booking, id_user, date_booking, booked_seats, id_flight) 
VALUES (1, 1, '2025-01-27T20:00:00', 2, 1),
       (2, 1, '2025-01-27T20:00:00', 1, 2);