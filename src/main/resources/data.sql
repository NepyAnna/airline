
/*Airports*/
INSERT INTO airports (id_airport,name_airport,code_iata_airport) VALUES (default, 'John F. Kennedy International Airport', 'JFK');
INSERT INTO airports (id_airport,name_airport,code_iata_airport) VALUES (default, 'Los Angeles International Airport', 'LAX');
INSERT INTO airports (id_airport,name_airport,code_iata_airport) VALUES (default,'Chicago O Hare International Airport', 'ORD');
INSERT INTO airports (id_airport,name_airport,code_iata_airport) VALUES (default, 'Hartsfield-Jackson Atlanta International Airport', 'ATL');

/*Flights*/
INSERT INTO flights (id_flight, id_departure_airport, id_arrival_airport, date_flight, status_flight, price, available_seats, total_seats) VALUES (default, 1, 2, '2025-02-12T19:32:00', 'AVAILABLE', 100.50, 150, 150);
INSERT INTO flights (id_flight, id_departure_airport, id_arrival_airport, date_flight, status_flight, price, available_seats, total_seats) VALUES (default, 2, 3, '2025-02-15T16:00:00', 'AVAILABLE', 50.75, 120, 150);
INSERT INTO flights (id_flight, id_departure_airport, id_arrival_airport, date_flight, status_flight, price, available_seats, total_seats) VALUES (default, 2, 4, '2025-02-11T16:00:00', 'AVAILABLE', 50.75, 120, 150);
INSERT INTO flights (id_flight, id_departure_airport, id_arrival_airport, date_flight, status_flight, price, available_seats, total_seats) VALUES (default, 4, 1, '2025-02-11T16:00:00', 'AVAILABLE', 140.75, 120, 120);
INSERT INTO flights (id_flight, id_departure_airport, id_arrival_airport, date_flight, status_flight, price, available_seats, total_seats) VALUES (default, 4, 3, '2025-01-30T16:00:00', 'AVAILABLE', 200.75, 120, 120);
INSERT INTO flights (id_flight, id_departure_airport, id_arrival_airport, date_flight, status_flight, price, available_seats, total_seats) VALUES (default, 3, 1, '2025-01-30T20:00:00', 'AVAILABLE', 300.00, 0, 100);

/* Roles */
INSERT INTO roles (id_role, name) VALUES (default, 'ROLE_USER');
INSERT INTO roles (id_role, name) VALUES (default, 'ROLE_ADMIN');

/* Users */
INSERT INTO users (id_user, username, password) VALUES (default, 'Din', '$2a$12$qanYcTsH3oRXTOPPKW0m9.s.tqyepncgMEDQb9JygZDcq8.Qx1zkG');
INSERT INTO users (id_user, username, password) VALUES (default, 'Don', '$2a$12$qanYcTsH3oRXTOPPKW0m9.s.tqyepncgMEDQb9JygZDcq8.Qx1zkG');

/* Profiles */
INSERT INTO profiles (id_profile, email, phone_number, address, user_id, photo, photo_url) VALUES (default,'din@mail.com', '123456', 'Round street, Square city', 1, null, 'https://postimg.cc/Y4hcfndB');
INSERT INTO profiles (id_profile, email, phone_number, address, user_id, photo, photo_url) VALUES (default,'don@mail.com', '123456', 'Round street, Square city', 2, null, 'https://postimg.cc/Y4hcfndB');


/* Roles Users */
INSERT INTO roles_users (role_id, user_id) VALUES (1, 1);
INSERT INTO roles_users (role_id, user_id) VALUES (2, 2);

/* Bookings */
INSERT INTO bookings (id_booking, id_user, date_booking, booked_seats, id_flight, status) VALUES (default, 1, '2025-01-27T20:00:00', 2, 1, 'CONFIRMED');
INSERT INTO bookings (id_booking, id_user, date_booking, booked_seats, id_flight, status) VALUES (default, 2, '2025-01-27T20:00:00', 1, 2, 'CONFIRMED');