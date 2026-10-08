package org.example.departmentservice.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {passwordValidator.class})
public @interface checkPassword {

    String message() default "{jakarta.validation.constraints.NotNull.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
