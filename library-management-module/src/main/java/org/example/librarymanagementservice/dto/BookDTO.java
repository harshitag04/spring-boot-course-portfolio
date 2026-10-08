package org.example.librarymanagementservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.example.librarymanagementservice.entities.Author;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private Long id;
    private String title;
    private Integer price;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate publishDate;
    private Author authorDTO;

}
