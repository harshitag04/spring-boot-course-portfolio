package org.example.departmentservice.advices;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Builder
@Data
public class APIError {

    private HttpStatus status;
    private String message;
    private List<String> suberrors;

}
