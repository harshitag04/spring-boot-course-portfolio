package org.example.librarymanagementservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookDTO {

    private String title;
    private Long price;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate publishDate;
    //private Long authorId;

}
