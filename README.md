# NGO Volunteer Management System

This is a Spring Boot application designed to manage NGO volunteers, reconstructed for seamless one-click Docker deployment on **Render**.

## Quick Links
- [Deployment Guide](docs/DEPLOYMENT.md)
- [Project Architecture](docs/ARCHITECTURE.md)
- [API Documentation](docs/API.md)

## Features
- Volunteer Registration and Management
- Admin Dashboard
- Twilio Integration for WhatsApp Notifications
- Dockerized for Easy Deployment

## Tech Stack
- **Backend:** Java 17, Spring Boot 3.2.5
- **Database:** MySQL
- **Frontend:** Thymeleaf
- **Deployment:** Docker, Render

## How to Run Locally
1. Ensure you have Docker installed.
2. Run `docker-compose up` (if a docker-compose file is provided) or build the image:
   ```bash
   docker build -t ngo-app .
   docker run -p 9090:9090 ngo-app
   ```
