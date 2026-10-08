package org.example.departmentservice.annotations;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class passwordValidator implements ConstraintValidator<checkPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }
        // Regex breakdown:
        // ^                 - Start of string
        // (?=.*[A-Z])       - Contains at least one uppercase letter
        // (?=.*[a-z])       - Contains at least one lowercase letter
        // (?=.*[^a-zA-Z0-9]) - Contains at least one special character (non-alphanumeric)
        // .{10,}            - Minimum length of 10 characters
        // $                 - End of string
        String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[^a-zA-Z0-9]).{10,}$";

        return password.matches(passwordRegex);
    }
}
