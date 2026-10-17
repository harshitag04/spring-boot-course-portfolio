package org.example.jwtauthentication.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "sessions",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_sessions_user_id",
                        columnNames = "user_id"
                )
        })
public class SessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", unique = true, nullable = false)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String token;
}
