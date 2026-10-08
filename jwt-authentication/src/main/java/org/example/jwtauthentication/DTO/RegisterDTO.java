package org.example.jwtauthentication.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {

    private String username;
    private String email;
    private String password;

}
