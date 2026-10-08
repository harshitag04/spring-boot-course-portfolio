package org.example.collegemanagementservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;

    @OneToMany(mappedBy = "professor")
    private List<Subject> subjects;

    @ManyToMany(mappedBy = "professors")
    private List<Student> students;

}
