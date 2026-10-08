package org.example.jwtauthentication.configs;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.jwtauthentication.entities.UserEntity;
import org.example.jwtauthentication.services.JWTService;
import org.example.jwtauthentication.services.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {

    private final JWTService jWTService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String requestTokenHeader  = request.getHeader("Authorization");

        if(requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        String token = requestTokenHeader.split("Bearer ")[1];
        Long userId = jWTService.getUserIdFromToken(token);

        if(userId != null && SecurityContextHolder.getContext().getAuthentication() != null){

            UserEntity user = userService.getUserById(userId);

            UsernamePasswordAuthenticationToken authenticationToken = new
                    UsernamePasswordAuthenticationToken(user, null, null);

            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}
