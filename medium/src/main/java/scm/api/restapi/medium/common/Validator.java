package scm.api.restapi.medium.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class Validator {

    public static Map<String, Object> parseErrorMessage(BindingResult validator){
        Map<String, Object> result = new HashMap<>();
        for(FieldError err: validator.getFieldErrors()) {
            result.put(err.getField(), err.getDefaultMessage());
        }
        return result;
    }
    
}
