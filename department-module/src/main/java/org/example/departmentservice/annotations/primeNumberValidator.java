package org.example.departmentservice.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class primeNumberValidator implements ConstraintValidator<checkPrimeNumber, Long> {


    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        // 0. Handle null values (validation fields can often be null)
        if (value == null) {
            return false;
        }

        long n = value;

        // 1. Check base cases (0, 1, and negative numbers are not prime)
        if (n <= 1) {
            return false;
        }

        // 2. 2 is the only even prime number
        if (n == 2) {
            return true;
        }

        // 3. Exclude all other even numbers
        if (n % 2 == 0) {
            return false;
        }

        // 4. Check odd factors up to the square root of n
        for (int i = 3; (long) i * i <= n; i += 2) {
            if (n % i == 0) {
                return false; // Found a factor, so it's not prime
            }
        }
        return true;
    }
}