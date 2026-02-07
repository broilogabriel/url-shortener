# URL Shortener Service - Ktor + Ktorm

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
- Java 21 or higher
- Gradle (or use included wrapper)

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
