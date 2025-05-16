# jwt-token-demo

**1. User Registration (/auth/signup)**
Client sends a POST request to /auth/signup with user details (email, password, full name).

Inside AuthController.register():

Check if the email already exists in the database (userRepository.findByEmail()).

If exists, throw an error ("email is already used").

If not, create a new UserDao object, set its fields (email, password, name).

Save the new user to the database (userRepository.save()).

Create an Authentication object (using email and password).

Put this Authentication into Spring Security's SecurityContextHolder.

Generate a JWT token for this authentication (JwtProvider.generateToken()).

Return the JWT token inside an AuthResponse to the client.

**2. Client Stores JWT**
The client receives the JWT token and stores it locally (e.g., in browser local storage or mobile app storage).

This token represents the user's authentication and is needed for future secured requests.

**3. Client Makes a Secured API Request**
The client makes a request to a secured endpoint, e.g., GET /api.

The client adds the JWT token in the request header:
Authorization: Bearer <token>

**4. JWT Validation Filter (JwtTokenValidator)**
When the request reaches your Spring Boot backend, the JwtTokenValidator filter runs before Spring Security processes authentication.

This filter:

Reads the Authorization header.

Extracts the JWT token (removes "Bearer " prefix).

Validates the token using the secret key.

Parses claims to extract email and user roles.

Creates an Authentication object with these details.

Sets this authentication in SecurityContextHolder so Spring Security knows the user is authenticated.

**5. Spring Security Authorizes the Request**
Now Spring Security sees that the user is authenticated.

It checks if the user has access to the endpoint (/api/** is protected).

Since the user is authenticated, the request proceeds.

**6. API Controller Responds**
The controller method mapped to /api runs.

It returns the protected resource (in your case, the string "Welcome to trading cesure").

**7. If Token is Invalid or Missing**
The filter will fail to parse or validate the JWT.

No authentication is set in the security context.

Spring Security rejects access to /api/** endpoints with a 401 Unauthorized or other error.

For open endpoints (like / or /auth/signup), no token is required.
