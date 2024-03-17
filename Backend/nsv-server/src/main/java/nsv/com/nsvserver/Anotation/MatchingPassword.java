package nsv.com.nsvserver.Anotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import nsv.com.nsvserver.Validator.MatchingPasswordValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = MatchingPasswordValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface MatchingPassword {
    String password();

    String confirmPassword();

    String message() default "Passwords do not matches";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
