package scm.api.restapi.medium.common.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ImageValidator.class})
public @interface Image {
    String message() default "Please choose image.";
    
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
