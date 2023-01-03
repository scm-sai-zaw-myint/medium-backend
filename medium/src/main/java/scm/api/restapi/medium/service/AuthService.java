package scm.api.restapi.medium.service;

import org.springframework.http.ResponseEntity;

import scm.api.restapi.medium.forms.UserForm;
import scm.api.restapi.medium.forms.request.AuthRequestForm;
import scm.api.restapi.medium.persistence.entiry.Users;

public interface AuthService {

    public ResponseEntity<?> login(AuthRequestForm form, String access_token);

    public ResponseEntity<?> registration(UserForm form, String access_token);
    
    public Users authUser(String token);
    
}
