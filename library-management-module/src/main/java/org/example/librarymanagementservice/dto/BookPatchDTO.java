package org.example.librarymanagementservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookPatchDTO {

    private String title;
    private Integer price;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate publishDate;
    private Long authorId;

}
