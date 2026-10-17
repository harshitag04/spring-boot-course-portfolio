package org.example.jwtauthentication.controllers;

import org.example.jwtauthentication.entities.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestController {

    @GetMapping("/api/profile")
    public Map<String, Object> profile(Authentication authentication) {

        UserEntity user = (UserEntity) authentication.getPrincipal();

        return Map.of(
                "message", "Authenticated successfully",
                "userId", user.getId(),
                "username", user.getUsername()
        );
    }

}
