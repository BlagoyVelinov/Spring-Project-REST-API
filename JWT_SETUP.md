# JWT Configuration for CinemaTickets-Movies Microservice

## Description
This microservice is configured to **validate JWT tokens**, but **NOT generate new tokens**. It acts as a validator of tokens sent by the core authentication microservice.

## How it works

### 1. JWT Validation
- The microservice receives a JWT token in the `Authorization: Bearer <token>` header
- Validates the token against the configured `jwt.secret`
- Extracts username and roles from the token
- Creates a Spring Security context for authentication

### 2. Roles and Permissions
- **ADMINISTRATOR** role has access to:
- `POST /api/movies/add-movie` - create a movie
- `DELETE /api/movies/delete-movie/{id}` - delete a movie
- `PUT /api/movies/update-projection-time/{id}` - update projections
- **Public endpoints** (without token):
- `GET /api/movies` - all movies
- `GET /api/movies/upcoming` - upcoming movies
- `GET /api/movies/movie/{id}` - specific movie

### 3. Configuration
```yaml
jwt:
secret: ${JWT_SECRET:ThisIsAVeryLongSuperSecretKeyThatIsAtLeast32Chars}
```

## Usage from Frontend

### 1. Get token
First you need to get JWT token from the main auth microservice:
```javascript
// Login request to the main auth microservice
const response = await fetch('http://auth-service/login', {
method: 'POST',
headers: { 'Content-Type': 'application/json' },
body: JSON.stringify({ username: 'admin', password: 'admin123' })
});
const { token } = await response.json();
```

### 2. Using a token
```javascript
// Request this microservice with a token
const response = await fetch('http://localhost:8081/api/movies/add-movie', {
method: 'POST',
headers: {
  'Content-Type': 'application/json',
  'Authorization': `Bearer ${token}`
},
body: JSON.stringify(movieData)
});
```

## Testing

### 1. Starting the application
```bash
./gradlew bootRun
```

### 2. Testing with curl
```bash
# Test without a token (should return 401)
curl -X POST http://localhost:8081/api/movies/add-movie \
-H "Content-Type: application/json" \
-d '{"name":"Test Movie","audio":"Angl.","description":"Test"}'

# Test with a valid token
curl -X POST http://localhost:8081/api/movies/add-movie \
-H "Content-Type: application/json" \
-H "Authorization: Bearer YOUR_JWT_TOKEN_HERE" \
-d '{"name":"Test Movie","audio":"Angl.","description":"Test"}'
```

### 3. Testing public endpoints
```bash
# Should work without a token
curl http://localhost:8081/api/movies
curl http://localhost:8081/api/movies/upcoming
```

## Debugging

### 1. Logs
DEBUG logs are enabled for:
  - `bg.softuni.CinemaTickets_Movies.config.JwtAuthFilter`
  - `bg.softuni.CinemaTickets_Movies.services.impl.JwtService`
  - `org.springframework.security`

### 2. Common problems

#### Problem: "Invalid credentials" or 401
**Causes:**
  - Missing Authorization header
  - Invalid JWT token
  - Expired token
  - Wrong JWT secret

**Solution:**
1. Check if the token is valid
2. Check if the JWT secret matches the one in the auth microservice
3. Check the logs for details

#### Problem: "Access Denied" or 403
**Causes:**
  - User does not have ADMINISTRATOR role
  - Token does not contain the correct roles

**Solution:**
1. Check if the token contains the "ADMINISTRATOR" role
2. Check the logs for the retrieved roles

## Important notes

1. **Does not generate tokens** - this microservice only validates
2. **Shared JWT secret** - must match the microservice auth
3. **Stateless** - does not store sessions
4. **CORS configured** - requests from localhost are allowed

## Environment Variables

```bash
# JWT Secret (required)
JWT_SECRET=your-super-secret-key-here

# Database (optional)
DB_HOST=localhost
DB_USERNAME=root
DB_PASSWORD=your-password
```