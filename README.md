# URL Shortener Service - Ktor + Ktorm

Project initially created using copilot using springboot but it seemed to be too much for such a simple project, so I
decided to rewrite it using Ktor and Ktorm. The rewrite was initially started using copilot but I had to make a lot of
changes to the generated code, due to outdated dependencies and missing layers in the generated code.

The code uses simple dependency injection using constructor injection and is organised in the following layers:

- Service layer: contains the business logic of the application
- Repository layer: contains the data access logic of the application
- Routes layer: contains the routing logic of the application

I don't have deep experience with Kotlin, only used it a small number of times in the past, and it is my first time
using Ktor and Ktorm.

I opted to use Ktor because it is a lightweight framework that allows me to quickly build a REST
API without the overhead of a full-fledged framework like Spring Boot. I also opted to use Ktorm because it seems like a
simple and lightweight ORM that allows me to easily interact with the database without having to write a lot of
boilerplate code.

## Design Decisions

- **Language**: Not a choice but a requirement for this project, I have very limited experience with Kotlin but I
  thought it would be doable to implement the solution in a reasonable amount of time.
- **In-memory Database**: I chose H2 for simplicity and ease of setup, as it allows the application to run without any
  external dependencies. Alternatives would require some containerization or additional setup.
- **Short Code Generation**: base62 encoding of the auto-incremented ID was chosen for simplicity and readability.
  Alternatives considered were UUIDs or hash-based codes, but they would not solve the issue of potential collisions out
  of the box.
- **Ktor**: Spring Boot seemed too much for such a simple project and Ktor seemed like a good fit for this.
- **Ktorm**: Similar to the previous point, it seemed simple enough and with decent features for a simple project.
- **Project Structure**: I opted for a simple layered architecture to keep the code organized and maintainable, even
  though the project is small. I'm not familiar if there are standard practices for Kotlin projects, but I tried to
  follow some common patterns I've applied in the past.
- **Dependency Injection**: I opted for constructor injection for simplicity and ease of testing, as it allows me to
  easily mock dependencies in unit tests without the need for a complex DI framework.
- **Error Handling**: Mostly handled by the auto generated code, I personally don't like the approach of throwing
  exceptions due to my experience with Scala (we tend to solve the problem differently). I decided to stick with it due
  to time constraints.
- **Testing**: Only unit tests for encoding and decoding of the short code. I didn't have time to write integration
  tests for the API, but it would be a good addition to ensure the correctness of the application.
- **Logging**: I didn't add any logging to the application, but it would be a good addition for debugging and monitoring
  purposes.

## Tech Stack

- **Language**: Kotlin
- **Framework**: Ktor
- **ORM**: Ktorm
- **Database**: H2 (in-memory)
- **Build Tool**: Gradle with Kotlin DSL
- **Server**: Netty (embedded)

## API Documentation

### 1. Shorten URL

```bash
POST /api/shorten
Content-Type: application/json

{
  "url": "https://example.com/very/long/url/path"
}
```

**Response (201 Created):**

```json
{
  "shortUrl": "http://localhost:8080/1",
  "originalUrl": "https://example.com/very/long/url/path",
  "shortCode": "1"
}
```

### 2. Redirect to Original URL

```bash
GET /{shortCode}
```

**Response**: 302 redirect to original URL

### 3. Get URL Information

```bash
GET /api/url/{shortCode}
```

**Response (200 OK):**

```json
{
  "originalUrl": "https://example.com/very/long/url/path",
  "shortCode": "1",
  "createdAt": "2024-01-20T10:30:00"
}
```

## Getting Started

### Prerequisites

The required versions for this project are available in the [.sdkmanrc](.sdkmanrc) file and can be installed using
[sdkman](https://sdkman.io) with the following command:

```shell
sdk env install
```

### Build & Run

**Build:**

```bash
./gradlew build
```

**Run:**

```bash
./gradlew run
```

The service starts on `http://localhost:8080`

### Usage Examples

**Shorten a URL:**

```bash
curl -X POST http://localhost:8080/api/shorten \
  -H "Content-Type: application/json" \
  -d '{"url": "https://github.com/kotlin/very/long/repository/path"}'
```

**Get URL info:**

```bash
curl http://localhost:8080/api/url/1
```

**Test redirect:**

```bash
curl -L http://localhost:8080/1
```
