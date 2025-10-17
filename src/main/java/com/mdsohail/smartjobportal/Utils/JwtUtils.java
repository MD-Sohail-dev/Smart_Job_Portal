package com.mdsohail.smartjobportal.Utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component // Marks this class as a Spring Bean so it can be injected where needed
public class JwtUtils {

    // Secret key for signing and verifying JWTs (keep it safe and private!)
    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    // Convert the secret key string into a SecretKey object
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // Extract the username (subject) from the JWT
    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    // Extract the expiration date from the JWT
    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    // Parse the JWT and extract all claims (payload data)
    private Claims extractAllClaims(String token) {
        return Jwts.parser()                      // Create a parser for JWT
                .verifyWith(getSigningKey())      // Verify token signature with secret key
                .build()                          // Build the parser
                .parseSignedClaims(token)         // Parse token and get signed claims
                .getPayload();                    // Get the payload (data)
    }

    // Check if the JWT is expired
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Generate a JWT for a specific username
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    // Create the JWT with claims, subject, issued date, expiration, and signature
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)                                           // Set custom claims
                .subject(subject)                                         // Set subject (username)
                .header().empty().add("typ", "JWT")                       // Set header type as JWT
                .and()
                .issuedAt(new Date(System.currentTimeMillis()))           // Set issued date
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Expire in 1 hour
                .signWith(getSigningKey())                                // Sign with secret key
                .compact();                                               // Build and return the token
    }

    // Validate token by checking if it's not expired
    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}
