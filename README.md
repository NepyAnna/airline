# Airline
The project aims to develop a management system for an airline using Spring with Spring Boot and Spring Security. This system will allow the comprehensive management of users, flights, reservations and destinations, with advanced features such as secure authentication using Basic Auth. The system cannot allow the selection of flights without available seats or that have passed the deadline. The project will be implemented using Java 21, Maven and MySQL or PostgreSQL.

## Some features

### User — Profile Relationship
Type of relationship:
One-to-One, bidirectional:
User is the "owner" of the profile (manages the lifecycle of the Profile).
The Profile exists only as an extension of the user's data.

#### Implementation details:
User manages the creation, update, and deletion of the profile (cascade = CascadeType.ALL).

Profile cannot exist without a User, but a User can exist without a fully completed Profile.

After a user is created, the profile is created separately (not automatically in the backend, but by client logic).

#### Business logic:
The profile is not created automatically in the registration service.
Responsibility for creating the Profile lies with the frontend.
After registering a user, the frontend must send a POST request to /api/profile.

#### Advantages of this approach:
Flexibility: the profile can be created/updated separately after registration.
Clean separation of responsibilities between modules: UserService does not depend on ProfileRepository.
Convenient for implementing multi-step registration or a separate profile settings form.
```java
// User.java
@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
private Profile profile;

// Profile.java
@OneToOne
@JoinColumn(name = "user_id", referencedColumnName = "id")
@JsonIgnore
private User user;
```

## Installation Steps
```bash
git clone https://github.com/NepyAnna/airline.git
cd airline
```
### Run

```bash
./mvnw spring-boot:run
```
or
```bash
mvn spring-boot:run
```
Alternative Way to Run the Application
If you are using an IDE such as IntelliJ IDEA,VS Code etc, you can simply click the “Run” button or run the main application class directly (the one annotated with @SpringBootApplication).
For example, in IntelliJ IDEA, right-click the main class and choose "Run 'AirlineApplication...main()'".


## API Endpoints

### Registration / Login / Logout
- POST http://localhost:8080/api/v1/registar registration of users.
- POST http://localhost:8080/api/v1/login login for users.
     http://localhost:8080/api/v1/login logout.

### Airports
- GET http://localhost:8080/api/v1/private/airports  to get list with all airports(Access: Available to all authenticated users).
- GET http://localhost:8080/api/v1/private/airports/{id} to get airport by ID(Access: Available to all authenticated users).
- GET http://localhost:8080/api/v1/private/airports/{codeIata} to get airport by code IATA(Access: Available to all authenticated users).
- POST http://localhost:8080/api/v1/private/airports  to add new airport(Only available to users with the ADMIN role).
- PUT http://localhost:8080/api/v1/private/airports/{id} to update airport data(Only available to users with the ADMIN role).
- DELETE http://localhost:8080/api/v1/private/airports to delete airport(Only available to users with the ADMIN role).
### Flights
- GET http://localhost:8080/api/v1/private/flights to get list with all flihgts(Access: Available to all authenticated users).
- GET http://localhost:8080/api/v1/private/flights/{id} to get flights by ID(Access: Available to all authenticated users).
- POST http://localhost:8080/api/v1/private/flights to add new flight(Only available to users with the ADMIN role).
- PUT http://localhost:8080/api/v1/private/flights/{id} to update flights data(Only available to users with the ADMIN role).
- DELETE http://localhost:8080/api/v1/private/flights to delete flight(Only available to users with the ADMIN role).
### Bookings
- GET http://localhost:8080/api/v1/bookings to get list with all bookings(Only available to users with the USER role).
- GET http://localhost:8080/api/v1/bookings/{id} to get booking by ID(Only available to users with the USER role).
- POST http://localhost:8080/api/v1/bookings to add new booking(Only available to users with the USER role).
- PUT http://localhost:8080/api/v1/bookings/{id} to update booking data(Only available to users with the USER role).
- DELETE http://localhost:8080/api/v1/bookings to delete booking by ID(Only available to users with the USER role).
### Users
- GET http://localhost:8080/api/v1/users  to get list with all(Only available to users with the ADMIN role).
- GET http://localhost:8080/api/v1/users/{id} to get user by ID(Only available to users with the USER and ADMIN role).
- PUT http://localhost:8080/api/v1/users/{id} to update user data(Only available to users with the USER role).
- DELETE http://localhost:8080/api/v1/users to delete  to delete user(Only available to users with the USER and ADMIN role).
### Profiles
- GET http://localhost:8080/api/v1/profiles  to get list with all profiles(Only available to users with the ADMIN role).
- GET http://localhost:8080/api/v1/profiles/{id}  to get profile by id(Only available to users with the USER and ADMIN role).
- POST http://localhost:8080/api/v1/profiles  to add new profile(Only available to users with the USER and ADMIN role).
- PUT http://localhost:8080/api/v1/profiles/{id} to update profile(Only available to users with the USER and ADMIN role).
- Delete http://localhost:8080/api/v1/profiles/{id} to delete profile by id.

## Running Tests
- Run the tests to validate the code functionality and observe test coverage.
- The project ensures more than 70% coverage across all methods.

[![temp-Image8-Z32s6.avif](https://i.postimg.cc/y8FMr2cp/temp-Image8-Z32s6.avif)](https://postimg.cc/G4mg47Mv)

## ER Diagram(Crow's feet)
[![temp-Image-WLvm4-U.avif](https://i.postimg.cc/gjG073B1/temp-Image-WLvm4-U.avif)](https://postimg.cc/ftgZtSsv)

## ClassDiagram
[![temp-Image-K8-Rn-U3.avif](https://i.postimg.cc/htrCmn9n/temp-Image-K8-Rn-U3.avif)](https://postimg.cc/Wdd6RQWY)

## Technology Stack
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)
![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![draw.io](https://img.shields.io/badge/draw.io-F08705?style=for-the-badge&logo=diagramsdotnet&logoColor=white)
![Cloudinary](https://img.shields.io/badge/cloudinary-3448C5?style=for-the-badge&logo=cloudinary&logoColor=white)
![Swagger](https://img.shields.io/badge/swagger-%2385EA2D.svg?style=for-the-badge&logo=swagger&logoColor=black)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

## Contributors
Anna Nepyivoda
     <a href="https://github.com/NepyAnna">
          <picture>
               <source srcset="https://img.icons8.com/ios-glyphs/30/ffffff/github.png" media="(prefers-color-scheme: dark)">
               <source srcset="https://img.icons8.com/ios-glyphs/30/000000/github.png" media="(prefers-color-scheme: light)">
               <img src="https://img.icons8.com/ios-glyphs/30/000000/github.png" alt="GitHub icon"/>
          </picture>
     </a>

## Disclaimer
This project is developed as part of a bootcamp learning experience and is intended for educational purposes only. The creators and contributors are not responsible for any issues, damages, or losses that may occur from using this code.
This project is not meant for commercial use, and any trademarks or references to third-party services (such as Funko) belong to their respective owners. By using this code, you acknowledge that it is a work in progress, created by learners, and comes without warranties or guarantees of any kind.

Use at your own discretion and risk.

Thank You! ❤️