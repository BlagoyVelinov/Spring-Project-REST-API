package bg.softuni.CinemaTickets_Movies.config;

import bg.softuni.CinemaTickets_Movies.services.impl.JwtService;
import bg.softuni.CinemaTickets_Movies.sessions.DummyUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);
    private final JwtService jwtService;

    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        logger.debug("Processing request: {} {}", request.getMethod(), request.getRequestURI());

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.debug("No valid Authorization header found");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        logger.debug("JWT token found, validating...");

        if (!jwtService.isTokenValid(token)) {
            logger.warn("Invalid JWT token provided");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String username = jwtService.extractUsername(token);
            List<String> roles = jwtService.extractRoles(token);
            logger.debug("Token valid for user: {} with roles: {}", username, roles);

            List<GrantedAuthority> authorities = roles.stream()
                    .map(role -> {
                        String roleWithPrefix = "ROLE_" + role;
                        logger.debug("Creating authority: {}", roleWithPrefix);
                        return new SimpleGrantedAuthority(roleWithPrefix);
                    })
                    .collect(Collectors.toList());
            
            logger.debug("Created authorities: {}", authorities);
            
            UserDetails dummyUser = new DummyUserDetails(username, authorities);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(dummyUser, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.debug("Authentication set for user: {} with authorities: {}", username, authorities);
        } catch (Exception e) {
            logger.error("Error processing JWT token", e);
        }

        filterChain.doFilter(request, response);
    }
}
