package org.example.collegemanagementservice.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdmissionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer fees;

    @OneToOne
    @JoinColumn(name = "student_id", unique = true)
    private Student student;
}
