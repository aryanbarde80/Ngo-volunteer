# API Documentation

This document outlines the key endpoints available in the NGO Volunteer Management System.

## Authentication
The application uses Spring Security. Access to certain endpoints may require login.

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/login` | GET | Displays the login page |
| `/logout` | GET | Logs out the current user |

## Volunteer Endpoints
| Endpoint | Method | Description |
|----------|--------|-------------|
| `/` | GET | Home page / Dashboard |
| `/register` | GET/POST | Volunteer registration |

## Admin Endpoints
| Endpoint | Method | Description |
|----------|--------|-------------|
| `/admin` | GET | Admin dashboard |
| `/admin/volunteers` | GET | View all registered volunteers |

*Note: For a full list of endpoints, please refer to the Controller classes in `src/main/java/com/rsai/controller/`.*
