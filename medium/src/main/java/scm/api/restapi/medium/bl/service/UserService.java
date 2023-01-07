package scm.api.restapi.medium.bl.service;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import scm.api.restapi.medium.forms.UserForm;

public interface UserService {

    ResponseEntity<?> getUserById(Integer id);

    ResponseEntity<?> updateUser(Integer id, UserForm form, BindingResult validator);

}
