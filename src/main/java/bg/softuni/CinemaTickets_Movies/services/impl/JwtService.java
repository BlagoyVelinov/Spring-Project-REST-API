package bg.softuni.CinemaTickets_Movies.services.impl;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtService {
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes();

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        try {
            String username = extractAllClaims(token).getSubject();
            logger.debug("Extracted username: {}", username);
            return username;
        } catch (Exception e) {
            logger.error("Error extracting username from token", e);
            throw e;
        }
    }

    public List<String> extractRoles(String token) {
        try {
            Object roles = extractAllClaims(token).get("roles");
            logger.debug("Raw roles from token: {}", roles);

            if (roles instanceof List<?>) {
                List<String> extractedRoles = ((List<?>) roles).stream()
                        .map(role -> {
                            if (role instanceof Map) {
                                Map<?, ?> roleMap = (Map<?, ?>) role;
                                Object authority = roleMap.get("authority");
                                if (authority != null) {
                                    String authorityStr = authority.toString();

                                    return authorityStr.startsWith("ROLE_") ? 
                                           authorityStr.substring(5) : authorityStr;
                                }
                            }
                            String roleStr = role.toString();
                            return roleStr.startsWith("ROLE_") ? 
                                   roleStr.substring(5) : roleStr;
                        })
                        .collect(Collectors.toList());

                logger.debug("Extracted roles: {}", extractedRoles);
                return extractedRoles;
            }

            logger.warn("No roles found in token or roles is not a list");
            return List.of();
        } catch (Exception e) {
            logger.error("Error extracting roles from token", e);
            return List.of();
        }
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractAllClaims(token);
            boolean isValid = !isExpired(claims);
            logger.debug("Token validation result: {}", isValid);
            return isValid;
        } catch (JwtException | IllegalArgumentException e) {
            logger.warn("Token validation failed: {}", e.getMessage());
            return false;
        }
    }

    private Claims extractAllClaims(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);

            return claimsJws.getPayload();
        } catch (Exception e) {
            logger.error("Error parsing JWT token", e);
            throw e;
        }
    }

    private boolean isExpired(Claims claims) {
        boolean expired = claims.getExpiration().before(new Date());
        if (expired) {
            logger.debug("Token is expired. Expiration: {}", claims.getExpiration());
        }
        return expired;
    }
}
