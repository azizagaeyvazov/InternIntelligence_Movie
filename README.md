# Movie Application

### Overview

The Movie Application is designed to manage movies and genres, allowing users to perform CRUD operations on movies and genres while ensuring a secure and verified user experience.

### Features

#### User Management

 - **Registration:** Users can register with a valid email address. A verification message is sent to the provided email for account activation.

 - **Email Verification:** Users must verify their email address to complete registration.

 - **Login:** Users can log in after successful registration and email verification.

 - **Forgot Password:** Users can reset their password by receiving a verification message sent to their registered email.

### Movie and Genre Management

 - **CRUD for Movies:** Users can create, read, update, and delete movie records.

 - **CRUD for Genres:** Users can create, read, update, and delete genres.

### API Documentation

 - **Swagger:** API endpoints are documented and can be tested directly in Swagger UI.

### Technologies Used

 - **Java 17**

 - **Spring Boot**

 - **Spring Security** (JWT Authentication)

 - **MapStruct** (Object Mapping)

 - **Hibernate** (Spring Data JPA)

 - **Swagger** (API Documentation and Testing)

- **PostgreSQL** Database (for local development)

 ### Setup and Installation

 #### Prerequisites

 - **Java 17 or later**

 - **Maven**

### Steps to Run

#### Clone the repository:

    ```bash
    git clone <repository-url>

    cd movie-application

#### Build the project:

    ```bash
    mvn clean install

### Run the application:

    ```bash
    mvn spring-boot:run

### Access the Swagger UI for API documentation and testing:

http://localhost:8080/swagger-ui/index.html