# Airline
The project aims to develop a management system for an airline using Spring with Spring Boot and Spring Security. This system will allow the comprehensive management of users, flights, reservations and destinations, with advanced features such as secure authentication using Basic Auth. The system cannot allow the selection of flights without available seats or that have passed the deadline. The project will be implemented using Java 21, Maven and MySQL or PostgreSQL.

## Some features

### User — Profile Relationship
**Type of relationship:**
One-to-One, bidirectional:
User is the "owner" of the profile (manages the lifecycle of the Profile).
The Profile exists only as an extension of the user's data.

#### Implementation details:
- The `User` entity manages the creation, update, and deletion of the `Profile` using `cascade = CascadeType.ALL`.
- A `Profile` cannot exist without a `User`.
- A `User` **always has an associated `Profile`** created automatically during registration.
- The `Profile` is initialized with the user's email and linked directly to the `User`.
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

#### Business logic:
During registration:

- The user provides a username, password, and email.
- A User object is created.
- A linked Profile object with the email is automatically created.
- A confirmation email is sent to the provided email address.
All of this happens inside the registration service (UserService), without requiring any separate client-side calls to create a profile.


### Email Notification Service
The application includes an email service that sends HTML-formatted booking confirmation emails using Thymeleaf templates.

#### Features:
- Sends a confirmation email to the user's profile email address after a successful registration or booking.
- Uses Thymeleaf (TemplateEngine, Context) to generate rich HTML content.
- Automatically triggered in UserService.save() or BookingService.createBooking() after saving.

#### Email Content:
Recipient: the user's email (retrieved from Profile)
Subject: Registration mail / Booking Confirmation
Body: generated from a Thymeleaf HTML template (booking-confirmation.html) and includes:
Username / Flight route and date / Number of booked seats / Booking status

#### Technology:
JavaMailSender for sending emails
Thymeleaf for dynamic HTML content rendering
Template: located in resources/templates/booking-confirmation.html

#### Flow:
User creates a booking via POST /api/bookings
Booking is saved to the database
EmailService.sendBookingConfirmationEmail() is called
Email is rendered from Thymeleaf template and sent to the user

## Installation Steps
```bash
git clone https://github.com/NepyAnna/airline.git
cd airline
```
### Run

- Environment Variables
The application requires a set of environment variables defined in a .env file.
An example configuration is provided in .env.example.
Steps:
Copy the example file:

```bash
cp .env.example .env
```
Fill in the values for the following variables:
DB_USERNAME - Database username
DB_PASSWORD - Database password
DB_URL - JDBC connection string to the database
SERVER_PORT - Port the application runs on
CLOUDINARY_CLOUD_NAME - Your Cloudinary cloud name
CLOUDINARY_API_KEY - Cloudinary API key
CLOUDINARY_API_SECRET - Cloudinary API secret
DEFAULT_PROFILE_IMAGE - URL to the default profile image
EMAIL - Email address for sending notifications
EMAIL_PASSWORD - Password or app-specific key for the notification email

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

### IntelliJ, Lombok and MapStruct: Important Note
If you are working in IntelliJ IDEA, please pay attention to the following configuration instructions, as you might encounter compilation issues — especially related to MapStruct and Lombok.
#### Problem:
When using Lombok (e.g. @Getter, @Setter, @Builder) together with MapStruct, IntelliJ may not recognize the generated getters/setters and other methods during compilation. As a result, IntelliJ may:
Show errors in @Mapper interfaces
Prevent project compilation (even though mvn compile works fine in the terminal)
Fail to suggest methods or fields in code completion

#### Solution:
##### Enable Lombok support:
Install the Lombok plugin in IntelliJ IDEA:
Settings → Plugins → Marketplace → Lombok → Install

##### Enable annotation processing:
Settings → Build, Execution, Deployment → Compiler → Annotation Processors
Make sure "Enable annotation processing" is checked.
Try checked off and then checked on again.

##### Enable MapStruct support:
In the same Annotation Processors menu, ensure that
"Obtain processors from project classpath" is selected.
Add the following MapStruct dependencies to your pom.xml:

```xml
<dependencies>
     <dependency>
          <groupId>org.mapstruct</groupId>
          <artifactId>mapstruct</artifactId>
          <version>1.5.5.Final</version>// check if actual
     </dependency>
     <dependency>
          <groupId>org.mapstruct</groupId>
          <artifactId>mapstruct-processor</artifactId>
          <version>1.5.5.Final</version>// check if actual
          <scope>provided</scope>
     </dependency>
</dependencies>
```
##### Additional recommendations:

If IntelliJ still doesn't recognize generated methods, try:
File → Invalidate Caches / Restart…

You can also force a full rebuild via:
Build → Rebuild Project

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
- PUT http://localhost:8080/api/v1/profiles/{id} to update profile(Only available to users with the USER and ADMIN role).
- Delete http://localhost:8080/api/v1/profiles/{id} to delete profile by id.

## Running Tests
- Unit Testing with JUnit and Mockito

The project includes comprehensive unit tests for service layer components, implemented using JUnit 5 and Mockito.
Mock objects are used to isolate the tested class from its dependencies, ensuring that the tests focus solely on the business logic without involving external systems such as databases or APIs.

Key aspects of the unit tests:
Mockito annotations such as @Mock and @InjectMocks simplify dependency injection for tests.
Behavior verification ensures that the correct repository and service methods are called with the expected arguments.
Exception testing validates that business rules and validation constraints trigger the correct exceptions.
Tests are fast and deterministic, since no external services are started.
This unit testing approach complements the integration tests (powered by Testcontainers), providing both isolated logic verification and realistic end-to-end scenarios.

- Integration Testing with Testcontainers
The project leverages Testcontainers to perform realistic integration testing of REST controllers and service layers.
Instead of relying on in-memory database MySQL, the tests spin up real service instances (PostgreSQL) in lightweight, disposable Docker containers.

Tests run minimizing environment-specific issues.
Each test execution is isolated and starts with a clean, reproducible state.
Containers are started automatically before the test suite and stopped afterwards, requiring no manual setup.
By using Testcontainers, the integration tests closely mirror production behavior, improving reliability and confidence in the application’s correctness.

Note:
Running the integration tests requires Docker to be installed and running on your system.
If Docker is not available, Testcontainers will not be able to start the required services, and the tests will fail.

## ERR Diagram
[![temp-Image-UR4-NJP.avif](https://i.postimg.cc/Hkyv3TPZ/temp-Image-UR4-NJP.avif)](https://postimg.cc/21CxSNYh)

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