# Project Architecture

The NGO Volunteer Management System follows a standard Spring Boot layered architecture.

## Layered Structure
- **Controller Layer:** Handles incoming HTTP requests and routes them to the appropriate service.
- **Service Layer:** Contains the business logic of the application.
- **Repository Layer:** Interfaces with the MySQL database using Spring Data JPA.
- **Model/Entity Layer:** Defines the data structures used throughout the application.

## Directory Structure
```text
ngo-volunteer-reconstructed/
├── src/
│   ├── main/
│   │   ├── java/          # Source code
│   │   └── resources/     # Configuration and templates
├── docs/                  # Project documentation
├── Dockerfile             # Docker image configuration
├── render.yaml            # Render blueprint configuration
└── pom.xml                # Maven dependencies
```

## Key Integrations
- **Spring Security:** Manages authentication and authorization.
- **Twilio SDK:** Used for sending WhatsApp notifications to volunteers.
- **Thymeleaf:** Server-side template engine for rendering the UI.
