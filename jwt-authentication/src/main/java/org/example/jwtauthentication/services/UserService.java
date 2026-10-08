package org.example.jwtauthentication.services;

import lombok.RequiredArgsConstructor;
import org.example.jwtauthentication.entities.UserEntity;
import org.example.jwtauthentication.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + username));
    }

    public UserEntity getUserById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(()->new UsernameNotFoundException("User not found with id "+userId));
    }
}
