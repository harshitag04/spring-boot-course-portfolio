package org.example.jwtauthentication.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.jwtauthentication.DTO.LoginDTO;
import org.example.jwtauthentication.DTO.RegisterDTO;
import org.example.jwtauthentication.DTO.UserDTO;
import org.example.jwtauthentication.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "apis/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/sign-up")
    public ResponseEntity<UserDTO> signUpController(@RequestBody RegisterDTO registerDTO){
         UserDTO savedUser = authService.signUp(registerDTO);
         return ResponseEntity.ok(savedUser);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> signUpController(@RequestBody LoginDTO loginDTO, HttpServletResponse httpServletResponse){
        String token = authService.logIn(loginDTO);

        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        httpServletResponse.addCookie(cookie);
        return ResponseEntity.ok(token);
    }

}
