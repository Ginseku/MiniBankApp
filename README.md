#  MiniBankApp

A RESTful banking backend application built with **Java 21** and **Spring Boot 3**, featuring JWT authentication, database migrations, and full Docker + CI/CD support.

---

## Tech Stack

|Category|Technology|
|---|---|
|Language|Java 21|
|Framework|Spring Boot 3.2.5|
|Security|Spring Security + JWT (jjwt 0.12.3)|
|Database|PostgreSQL (Neon cloud)|
|ORM|Spring Data JPA|
|Migrations|Flyway|
|Build Tool|Gradle (Kotlin DSL)|
|Containerization|Docker + Docker Compose|
|CI/CD|GitHub Actions|
|Utilities|Lombok, Bean Validation|

---

## Features

- 🔐 **JWT Authentication** — secure user registration and login
- 🗄️ **Database Migrations** — version-controlled schema with Flyway
- ✅ **Input Validation** — request validation with Spring Validation
- 🐳 **Docker Support** — multi-stage Dockerfile for optimized image size
- ⚙️ **CI/CD Pipeline** — automated build and Docker image creation via GitHub Actions
- ☁️ **Cloud Database** — connected to Neon PostgreSQL (serverless)

---

## 📁 Project Structure

```
MiniBankApp/
├── .github/workflows/       # GitHub Actions CI/CD
├── src/
│   └── main/
│       ├── java/            # Application source code
│       └── resources/       # Config & Flyway migrations
├── Dockerfile               # Multi-stage Docker build
├── docker-compose.yml       # Local deployment config
├── .env.example             # Environment variables template
└── build.gradle.kts         # Gradle build configuration
```

---

## ⚙️ Getting Started

### Prerequisites

- Java 21+
- Docker & Docker Compose
- PostgreSQL database (or use [Neon](https://neon.tech/) cloud)

### 1. Clone the repository

```bash
git clone https://github.com/Ginseku/MiniBankApp.git
cd MiniBankApp
```

### 2. Configure environment variables

```bash
cp .env.example .env
```

Edit `.env` with your database credentials:

```env
NEON_USER=your_db_user
NEON_PASSWORD=your_db_password
```

### 3. Run with Docker Compose

```bash
docker-compose up --build
```

The application will be available at `http://localhost:8080`

### 4. Run locally (without Docker)

```bash
./gradlew bootRun
```

---

## 🔄 CI/CD

The project uses **GitHub Actions** for continuous integration. On every push to `main`, the pipeline:

1. Builds the project with Gradle
2. Runs tests
3. Builds a Docker image

See `.github/workflows/` for configuration details.

---

## 🛡️ Security

- Passwords are stored hashed (never in plain text)
- JWT tokens are used for stateless authentication
- Environment variables are used for all sensitive credentials (never hardcoded)
- `.env` file is excluded from version control via `.gitignore`

---

## 📬 API Endpoints

> Full API documentation coming soon

|Method|Endpoint|Description|Auth Required|
|---|---|---|---|
|POST|`/api/auth/register`|Register a new user|❌|
|POST|`/api/auth/login`|Login and receive JWT token|❌|
|GET|`/api/...`|Banking operations|✅|

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

---

## 👤 Author

**Mykyta Bondarchuk**

- GitHub: [@Ginseku](https://github.com/Ginseku)
- LinkedIn: [mykyta-bondarchuk](https://www.linkedin.com/in/mykyta-bondarchuk-a61150268/)
