package bg.softuni.CinemaTickets_Movies.services.impl;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes();

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public List<String> extractRoles(String token) {
        Object roles = extractAllClaims(token).get("roles");

        if (roles instanceof List<?>) {
            List<String> extractedRoles = ((List<?>) roles).stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());

            return extractedRoles;
        }

        return List.of();
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return !isExpired(claims);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims extractAllClaims(String token) {
        Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token);

        return claimsJws.getPayload();
    }

    private boolean isExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }
}
