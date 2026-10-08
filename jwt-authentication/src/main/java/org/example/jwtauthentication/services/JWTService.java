package org.example.jwtauthentication.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.example.jwtauthentication.entities.UserEntity;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.smartcardio.ATR;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Set;

@Service
public class JWTService {

    private static final String jwtSecretKey = "djkhkfuerh93eue83jnc9e8rew90c98449fvvfd";

    private SecretKey generateSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserEntity user){
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("roles", Set.of("ADMIN", "USER"))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 60*1000))
                .signWith(generateSecretKey())
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        Claims claim = Jwts.parser()   // to parse it
                .verifyWith(generateSecretKey())// verify the key that we have
                .build()   // build the parser
                .parseSignedClaims(token)   //parse the token and get jws<claims>
                .getPayload();  //to get the payload

        return Long.valueOf(claim.getSubject());  //Assuming the user ID is stored as the subject in the token

    }

}
