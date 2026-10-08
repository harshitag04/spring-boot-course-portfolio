package org.example.librarymanagementservice.advices;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class APIResponse<T> {

    private String status;
    private APIError error;
    private LocalDateTime timestamp;
    private T data;

    public APIResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public APIResponse(APIError error) {
        this();
        this.error = error;
    }

    public APIResponse(T data) {
        this();
        this.data = data;
    }
}
