package scm.api.restapi.medium.bl.service;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import scm.api.restapi.medium.forms.PasswordForm;
import scm.api.restapi.medium.forms.UserForm;
import scm.api.restapi.medium.forms.request.AuthRequestForm;
import scm.api.restapi.medium.persistence.entiry.Users;

public interface AuthService {

    public ResponseEntity<?> login(AuthRequestForm form, String access_token);

    public ResponseEntity<?> registration(UserForm form, String access_token, BindingResult validator);
    
    public Users authUser(String token);

    public ResponseEntity<?> getUserInfo();

    public ResponseEntity<?> changePassword(PasswordForm form, String access_token, BindingResult validator);

    public ResponseEntity<?> logout();
    
}
