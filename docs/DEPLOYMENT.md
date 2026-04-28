# Deployment Guide

This project is configured for a **one-click deployment** on Render using the provided `render.yaml` file.

## Render Deployment Steps

1. **Push to GitHub:** Ensure this reconstructed code is pushed to your GitHub repository.
2. **Connect to Render:**
   - Log in to your [Render Dashboard](https://dashboard.render.com/).
   - Click on **New +** and select **Blueprint**.
   - Connect your GitHub repository.
3. **Automatic Setup:**
   - Render will detect the `render.yaml` file.
   - It will automatically provision a **MySQL Database** and a **Web Service**.
   - The Dockerfile will be used to build and deploy the Spring Boot application.

## Configuration Details
- **Port:** The application runs on port `9090`.
- **Secrets:** All secrets (Twilio, Database credentials) have been hardcoded into `src/main/resources/application.properties` as per your request.
- **Database:** The application is configured to connect to a service named `ngo-db` on port `3306`.

## Manual Docker Deployment
If you wish to deploy manually using Docker:
```bash
docker build -t ngo-volunteer-app .
docker run -p 9090:9090 ngo-volunteer-app
```
