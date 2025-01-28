
/*Airports*/
INSERT INTO airports (id_airport,name_airport,code_iata_airport) VALUES (1, 'John F. Kennedy International Airport', 'JFK');
INSERT INTO airports (id_airport,name_airport,code_iata_airport) VALUES (2, 'Los Angeles International Airport', 'LAX');
INSERT INTO airports (id_airport,name_airport,code_iata_airport) VALUES (3,'Chicago O Hare International Airport', 'ORD');
INSERT INTO airports (id_airport,name_airport,code_iata_airport) VALUES (4, 'Hartsfield-Jackson Atlanta International Airport', 'ATL');

/*Flights*/
INSERT INTO flights (id_flight, id_departure_airport, id_arrival_airport, date_flight, status_flight, price, available_seats, total_seats) VALUES (1, 1, 2, '2025-01-24T10:21:00', 'AVAILABLE', 300.50, 150, 200);
INSERT INTO flights (id_flight, id_departure_airport, id_arrival_airport, date_flight, status_flight, price, available_seats, total_seats) VALUES (2, 2, 3, '2025-01-26T15:00:00', 'AVAILABLE', 450.75, 120, 150);
INSERT INTO flights (id_flight, id_departure_airport, id_arrival_airport, date_flight, status_flight, price, available_seats, total_seats) VALUES (3, 3, 1, '2025-01-27T20:00:00', 'CANCELLED', 500.00, 0, 100);

/* Roles */
INSERT INTO roles (id_role, name) VALUES (1, 'ROLE_USER');
INSERT INTO roles (id_role, name) VALUES (2, 'ROLE_ADMIN');

/* Users */
INSERT INTO users (id_user, username, password) VALUES (1, 'Din', '$2a$12$qanYcTsH3oRXTOPPKW0m9.s.tqyepncgMEDQb9JygZDcq8.Qx1zkG');
INSERT INTO users (id_user, username, password) VALUES (2, 'Don', '$2a$12$qanYcTsH3oRXTOPPKW0m9.s.tqyepncgMEDQb9JygZDcq8.Qx1zkG');

/* Profiles */
INSERT INTO profiles (id_profile, email, phone_number, address, user_id, photo) VALUES (1,'din@mail.com', '123456', 'Round street, Square city', 1, null);
INSERT INTO profiles (id_profile, email, phone_number, address, user_id, photo) VALUES (2,'don@mail.com', '123456', 'Round street, Square city', 2, null);


/* Roles Users */
INSERT INTO roles_users (role_id, user_id) VALUES (1, 1);
INSERT INTO roles_users (role_id, user_id) VALUES (2, 2);

/* Bookings */
INSERT INTO bookings (id_booking, id_user, date_booking, booked_seats, id_flight, status) VALUES (1, 1, '2025-01-27T20:00:00', 2, 1, 'CONFIRMED');
INSERT INTO bookings (id_booking, id_user, date_booking, booked_seats, id_flight, status) VALUES (2, 1, '2025-01-27T20:00:00', 1, 2, 'CONFIRMED');