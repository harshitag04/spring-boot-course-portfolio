package org.example.departmentservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.departmentservice.annotations.checkPassword;
import org.example.departmentservice.annotations.checkPrimeNumber;

@Setter
@Getter
public class DepartmentDTO {

    @checkPrimeNumber(message = "Must be a Prime Number")
    private Long id;
    @NotNull(message = "Title field is required")
    @NotBlank(message = "Title field is required")
    private String title;
    private boolean isActive;
    @NotNull
    @NotBlank
    @checkPassword(message = "Must contains at least one uppercase letter" + "\n" +
            "Must contains at least one lowercase letter" + "\n" +
            "Must contains at least one special character (non-alphanumeric)" + "\n" +
            "Minimum length of 10 characters" + "\n")
    private String departmentPassword;

}
