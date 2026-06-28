# job-application-tracker
# Job Application Tracker

A full-stack job application tracking system built with React, Material UI, Spring Boot, PostgreSQL, and JWT authentication.

## Features

- User registration and login
- JWT-protected REST APIs
- Add, search, and delete job applications
- Track status: Applied, Interview, Offer, Rejected
- Follow-up date and notes
- Dashboard summary cards
- PostgreSQL database
- Docker Compose for local database
- JUnit/Mockito backend test example

## Tech Stack

**Frontend**
- React
- Material UI
- Axios
- React Router
- Vite

**Backend**
- Java 17
- Spring Boot 3
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT
- JUnit / Mockito

## Architecture


React UI -> Spring Boot REST API -> PostgreSQL Database


## Project Structure


job-application-tracker/
  backend/
    src/main/java/com/example/jobtracker
    src/main/resources/application.yml
    pom.xml
  frontend/
    src/pages
    src/components
    src/api
    package.json
  docker-compose.yml


## Run Locally

### 1. Start PostgreSQL


docker compose up -d


### 2. Run Backend


cd backend
mvn spring-boot:run


Backend runs on:


http://localhost:8080


### 3. Run Frontend


cd frontend
npm install
npm run dev


Frontend runs on:


http://localhost:5173


## Main API Endpoints


POST /api/auth/register
POST /api/auth/login

GET    /api/applications
POST   /api/applications
PUT    /api/applications/{id}
DELETE /api/applications/{id}

GET /api/applications/status/{status}
GET /api/applications/search?keyword=java
GET /api/applications/dashboard


## Sample Application JSON


{
  "companyName": "Google",
  "jobTitle": "Java Developer",
  "location": "Boston, MA",
  "status": "APPLIED",
  "appliedDate": "2026-06-22",
  "followUpDate": "2026-06-29",
  "jobLink": "https://example.com/job",
  "notes": "Applied through company career site"
}


## Future Enhancements

- Edit application from UI
- Email reminder for follow-up date
- File upload for resume version
- CI/CD with GitHub Actions
- Deployment to AWS, Render, or Railway
- Pagination and sorting
- Role-based access control

## Why This Project Is Useful

This project demonstrates real-world software engineering skills: REST API design, authentication, database modeling, frontend state management, validation, exception handling, testing, and Docker-based local development.

