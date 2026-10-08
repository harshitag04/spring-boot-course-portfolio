package org.example.librarymanagementservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Column(unique = true, nullable = false)
    private String title;

    @Min(200)
    private Integer price;

    @Column(name = "publish_date")
    private LocalDate publishDate;

    @PrePersist
    protected void onCreate() {
        if (this.publishDate == null) {
            this.publishDate = LocalDate.now();
        }
    }

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;


}
