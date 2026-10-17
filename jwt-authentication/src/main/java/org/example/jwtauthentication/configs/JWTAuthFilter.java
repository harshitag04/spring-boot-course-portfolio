package org.example.jwtauthentication.configs;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.jwtauthentication.entities.SessionEntity;
import org.example.jwtauthentication.entities.UserEntity;
import org.example.jwtauthentication.repositories.SessionRepository;
import org.example.jwtauthentication.services.JWTService;
import org.example.jwtauthentication.services.UserService;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {

    private final JWTService jWTService;
    private final UserService userService;
    private final SessionRepository sessionRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String requestTokenHeader  = request.getHeader("Authorization");

        if(requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        String token = requestTokenHeader.split("Bearer ")[1];
        Long userId = jWTService.getUserIdFromToken(token);

        // 2. Check whether this exact token is the active session.
        Optional<SessionEntity> activeSession =
                sessionRepository.findByToken(token);

        if (activeSession.isEmpty()
                || !userId.equals(activeSession.get().getUserId())) {
            SecurityContextHolder.clearContext();

            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Invalid, expired, or inactive token"
            );
            return;
        }

        if(SecurityContextHolder.getContext().getAuthentication() == null){

            UserEntity user = userService.getUserById(userId);

            UsernamePasswordAuthenticationToken authentication = new
                    UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private void reject(HttpServletResponse response)
            throws IOException {

        SecurityContextHolder.clearContext();
        response.sendError(
                HttpServletResponse.SC_UNAUTHORIZED,
                "Invalid, expired, or inactive token"
        );
    }
}
