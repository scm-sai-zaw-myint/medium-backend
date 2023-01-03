package scm.api.restapi.medium.service;

import org.springframework.http.ResponseEntity;

import scm.api.restapi.medium.forms.UserForm;

public interface UserService {

    ResponseEntity<?> getUserById(Integer id);

    ResponseEntity<?> updateUser(Integer id, UserForm form);

}
