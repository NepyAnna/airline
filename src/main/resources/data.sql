INSERT INTO airports (id_airport,name_airport,code_iata_airport) VALUES 
    (1, 'John F. Kennedy International Airport', 'JFK'),
    (2, 'Los Angeles International Airport', 'LAX'),
    (3,'Chicago O Hare International Airport', 'ORD'),
    (4, 'Hartsfield-Jackson Atlanta International Airport', 'ATL');

INSERT INTO flights (id_flight, id_departure_airport, id_arrival_airport, date_flight, status_flight, available_seats, total_seats) VALUES
    (1, 1, 2, '2025-02-01T15:30:00', 'AVAILABLE', 100, 180),
    (2, 3, 4, '2025-02-02T18:45:00', 'AVAILABLE', 150, 210),
    (3, 2, 1, '2025-02-03T09:00:00', 'AVAILABLE', 50, 180),
    (4, 4, 3, '2025-02-04T22:15:00', 'UNAVAILABLE', 0, 180);
    
INSERT INTO roles (id_role, name) VALUES (default, 'ROLE_USER');
INSERT INTO roles (id_role, name) VALUES (default, 'ROLE_ADMIN');

INSERT INTO users (id_user, username, password) VALUES (default, 'pepe', '$2a$12$8LegtLQWe717tIPvZeivjuqKnaAs5.bm0Q05.5GrAmcKzXw2NjoUO');
INSERT INTO users (id_user, username, password) VALUES (default, 'pepa', '$2a$12$8LegtLQWe717tIPvZeivjuqKnaAs5.bm0Q05.5GrAmcKzXw2NjoUO');

INSERT INTO profiles (id_profile,email, address, user_id) VALUES (default,'pepe@mail.com', 'portal 1',1);
INSERT INTO profiles (id_profile,email, address, user_id) VALUES (default,'pepa@mail.com', 'portal 1',2);

INSERT INTO roles_users (role_id, user_id) VALUES 
(1, 1),
(2, 2)


INSERT INTO bookings (id_booking, id_user, date_booking, booked_seats) VALUES
    (1, 1, '2025-02-01T15:30:00', 2),
    (2, 2, '2025-02-02T18:45:00', 4);

INSERT INTO bookings_flights (booking_id, flight_id) VALUES
    (1, 1),
    (2, 2);