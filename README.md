# CollaboratorBook — Backend (Spring Boot microservices)

Internal social network and HR platform for companies, built as my end-of-studies project
(PFE, Ditriot Consulting, 2022). Employees share posts and comments; HR manages employees,
contracts, leave, projects, documents and claims; the business service models companies,
departments, offices and jobs.

Frontend (Angular): [CollaboratorBook_FRONT](https://github.com/rachedchakchouk/CollaboratorBook_FRONT)

## Architecture

```
                 ┌──────────────────┐
 Angular SPA ──▶ │  cloud-gateway   │ :8888  (Spring Cloud Gateway, CORS)
                 └────────┬─────────┘
                          │  service discovery
                 ┌────────▼─────────┐
                 │  eureka-server   │ :8761
                 └────────┬─────────┘
        ┌─────────────────┼──────────────────┐
┌───────▼───────┐ ┌───────▼───────┐ ┌────────▼───────┐
│  securityMs   │ │     botRH     │ │   businessMS   │
│ :8087  JWT    │ │ :8081  HR     │ │ :8082 business │
│ security_db   │ │ rh_db         │ │ business_db    │
└───────────────┘ └───────────────┘ └────────────────┘
```

| Service | Port | Responsibility |
|---|---|---|
| `eureka-server` | 8761 | Service registry (Netflix Eureka) |
| `cloud-gateway` | 8888 | Single entry point, routing through discovery, CORS |
| `securityMs` | 8087 | Users and roles, login, JWT access / refresh tokens (Spring Security, BCrypt) |
| `botRH` | 8081 | HR: employees, contracts and clauses, leave, projects, documents, posts, comments, notifications, claims, statistics |
| `businessMS` | 8082 | Companies, departments, offices, jobs |

Each business service follows a layered design: `controller` → `service` → `repo` (Spring Data JPA),
with request / response DTOs mapped by **MapStruct**, and inter-service calls through **OpenFeign**.

## Tech stack

Java 8 · Spring Boot 2.7 · Spring Cloud 2021 (Eureka, Gateway, OpenFeign) · Spring Security · JWT (auth0 java-jwt)
· Spring Data JPA / Hibernate · MySQL · MapStruct · Lombok · Swagger (Springfox) · Maven

## Configuration

Secrets are read from environment variables — nothing sensitive is committed.

| Variable | Used by | Description |
|---|---|---|
| `JWT_SECRET` | securityMs | HMAC key used to sign and verify JWTs (**required**, at least 32 characters) |
| `DB_USERNAME` | securityMs, botRH, businessMS | MySQL user (default `root`) |
| `DB_PASSWORD` | securityMs, botRH, businessMS | MySQL password (default empty) |

Generate a JWT secret, for example:

```bash
export JWT_SECRET=$(openssl rand -base64 48)
```

## Run locally

Prerequisites: JDK 8+, Maven, MySQL running on `localhost:3306` (databases are created automatically).

Start the services in this order, each in its own terminal:

```bash
cd eureka-server && ./mvnw spring-boot:run
cd cloud-gateway && ./mvnw spring-boot:run
cd securityMs    && ./mvnw spring-boot:run
cd botRH         && ./mvnw spring-boot:run
cd businessMS    && ./mvnw spring-boot:run
```

Eureka dashboard: http://localhost:8761

## Main API routes

| Service | Base path |
|---|---|
| securityMs | `/api` — `POST /api/login`, token refresh, users |
| botRH | `/RH/employees`, `/RH/contracts`, `/RH/clauses`, `/RH/holidays`, `/RH/projects`, `/RH/documents`, `/RH/post`, `/RH/comment`, `/RH/notifications`, `/RH/Reclamations` |
| businessMS | `/business/Companies`, `/business/Departments`, `/business/offices`, `/business/jobs` |

## License

All rights reserved — the code is shared for portfolio review only. See [LICENSE](LICENSE).

## Author

**Rached Chakchouk** — Full Stack Software Engineer (Java / Spring Boot / Angular)
[LinkedIn](https://www.linkedin.com/in/rached-chakchouk) · [Portfolio](https://rached-chakchouk.netlify.app)
