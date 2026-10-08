package org.example.jwtauthentication.services;

import lombok.RequiredArgsConstructor;
import org.example.jwtauthentication.DTO.LoginDTO;
import org.example.jwtauthentication.DTO.RegisterDTO;
import org.example.jwtauthentication.DTO.UserDTO;
import org.example.jwtauthentication.entities.UserEntity;
import org.example.jwtauthentication.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public UserDTO signUp(RegisterDTO registerDTO) {

        if(userRepository.findByUsername(registerDTO.getUsername()).isPresent()){
            throw new RuntimeException("User with username " + registerDTO.getUsername() + " already exist");
        }
        if(userRepository.findByEmail(registerDTO.getEmail()).isPresent()){
            throw new RuntimeException("The " + registerDTO.getEmail() + " already exists");
        }

        UserEntity userToBeSaved = modelMapper.map(registerDTO, UserEntity.class);
        userToBeSaved.setPassword(passwordEncoder.encode(userToBeSaved.getPassword()));
        UserEntity savedUser = userRepository.save(userToBeSaved);

        return modelMapper.map(savedUser, UserDTO.class);

    }

    public String logIn(LoginDTO loginDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken
                (loginDTO.getUsername(), loginDTO.getPassword()));
        UserEntity user = (UserEntity) authentication.getPrincipal();
        return jwtService.generateToken(user);
    }
}
