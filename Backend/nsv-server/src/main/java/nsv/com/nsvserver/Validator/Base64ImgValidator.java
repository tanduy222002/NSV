package nsv.com.nsvserver.Validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import nsv.com.nsvserver.Annotation.Base64Img;

public class Base64ImgValidator implements ConstraintValidator<Base64Img, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return password.matches("^(data:image)/(?:jpeg|png);base64,(.+)$");
    }
}
