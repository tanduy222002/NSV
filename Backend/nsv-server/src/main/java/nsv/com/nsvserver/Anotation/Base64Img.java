package nsv.com.nsvserver.Anotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import nsv.com.nsvserver.Validator.Base64ImgValidator;
import nsv.com.nsvserver.Validator.StrongPasswordValidator;

import java.lang.annotation.*;

@Constraint(validatedBy = Base64ImgValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Base64Img {
    String message() default "image must start with prefix: 'data:image/jpeg;base64,' or 'data:image/png;base64,' ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
