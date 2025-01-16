INSERT INTO airports (id_airport, code_iata_airport, name_airport) VALUES 
    (1, 'JFK', 'John F. Kennedy International Airport'),
    (2, 'LAX', 'Los Angeles International Airport'),
    (3, 'ORD', 'Chicago O Hare International Airport'),
    (4, 'ATL', 'Hartsfield-Jackson Atlanta International Airport');

-- Then, insert into flights
INSERT INTO flights (id_flight, id_departure_airport, id_arrival_airport, date_flight, status_flight, available_seats) VALUES
    (1, 1, 2, '2025-02-01T15:30:00', 'AVAILABLE', 100),
    (2, 3, 4, '2025-02-02T18:45:00', 'AVAILABLE', 150),
    (3, 2, 1, '2025-02-03T09:00:00', 'AVAILABLE', 50),
    (4, 4, 3, '2025-02-04T22:15:00', 'CANCELLED', 0);