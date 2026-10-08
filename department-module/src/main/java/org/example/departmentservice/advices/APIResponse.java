package org.example.departmentservice.advices;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class APIResponse<T> {

    @JsonFormat(pattern = "hh-mm-ss dd-mm-yyyy")
    private LocalDateTime timestamp;
    private T data;
    private APIError error;

    public APIResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public APIResponse(T data) {
        this();
        this.data = data;
    }

    public APIResponse(APIError error) {
        this();
        this.error = error;
    }
}
