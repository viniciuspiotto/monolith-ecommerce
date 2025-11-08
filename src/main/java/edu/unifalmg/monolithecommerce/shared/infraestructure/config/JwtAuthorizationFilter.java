package edu.unifalmg.monolithecommerce.shared.infraestructure.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final TokenValidationService tokenValidationService;

    @Value("${api.security.cookie.name}")
    private String jwtCookieName;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String token = extractToken(request);

        if (token == null) {
            log.trace("No JWT token found in request header or cookie. Proceeding with filter chain.");
            filterChain.doFilter(request, response);
            return;
        }

        DecodedJWT decodedJWT = tokenValidationService.validateToken(token);

        if (decodedJWT != null) {
            String customerId = tokenValidationService.getClaim(decodedJWT, "customerId");

            if (customerId == null) {
                log.warn("Invalid JWT token: 'customerId' claim is missing.");
                SecurityContextHolder.clearContext();
                filterChain.doFilter(request, response);
                return;
            }

            List<String> roles = tokenValidationService.getRoles(decodedJWT);
            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    customerId,
                    null,
                    authorities
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("JWT token validated successfully for user: {}", customerId);
        } else {
            log.warn("Invalid JWT token received.");
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (jwtCookieName.equals(cookie.getName())) {
                    log.trace("JWT token found in cookie.");
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}