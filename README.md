# 🎬 Cinema Tickets Movies REST-API

This repository contains the **Cinema Tickets Movies Service**, a microservice responsible for managing and displaying movies in the **Cinema Tickets** application.

The service is built with **Spring Boot** and uses **MySQL** as its database.  
It is part of a microservices architecture and works together with the main backend service:

- 🗂 [Cinema Tickets Backend (Spring-Project)](https://github.com/BlagoyVelinov/Spring-Project)
- 💻 [Cinema Tickets Frontend (React)](https://github.com/BlagoyVelinov/CinemaTickets-React)

Together, the two backend services (`Spring-Project` and `Cinema Tickets Movies`) form the complete API for the React frontend.

---

## ⚙️ Features
- Manage movies (create, update, delete)
- Retrieve movie details
- Provide movie data to the main backend service
- Designed as part of a microservices-based architecture

---

## 🗄️ Database
This service requires a **MySQL 8.0** database to run.  
By default, the Docker setup will start a MySQL container alongside the service.

Default credentials (can be changed in `docker-compose.yml` or `.env`):
- **Username:** `root`
- **Password:** `root`
- **Database name:** `cinema_movies`

---

## 🚀 Run with Docker

If you want to run the **Cinema Tickets Movies** service using Docker, you can pull the prebuilt image from Docker Hub:

```bash
docker pull blagoyvelinov/cinema-tickets-movies
```

- More information about docker image you can see here: [Cinema Tickets Movies - Docker Hub](https://hub.docker.com/repository/docker/blagoyvelinov/cinema-tickets/general)