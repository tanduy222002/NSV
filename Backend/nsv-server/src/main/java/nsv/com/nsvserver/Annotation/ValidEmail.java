package nsv.com.nsvserver.Annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import nsv.com.nsvserver.Validator.ValidEmailValidator;

import java.lang.annotation.*;

@Constraint(validatedBy = ValidEmailValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidEmail {
    String message() default "This is a invalid email";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
