package scm.api.restapi.medium.service;

import org.springframework.http.ResponseEntity;

import scm.api.restapi.medium.forms.UserForm;
import scm.api.restapi.medium.forms.request.AuthRequestForm;

public interface AuthService {

    ResponseEntity<?> login(AuthRequestForm form, String access_token);

    ResponseEntity<?> registration(UserForm form, String access_token);
    
}
