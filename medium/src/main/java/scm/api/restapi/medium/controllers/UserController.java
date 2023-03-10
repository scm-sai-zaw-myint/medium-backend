package scm.api.restapi.medium.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.Valid;
import scm.api.restapi.medium.bl.service.UserService;
import scm.api.restapi.medium.forms.ProfileUpdateForm;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Integer id,@Nullable@RequestParam Integer page){
        return this.userService.getUserById(id, page);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id,@Valid@ModelAttribute ProfileUpdateForm form,BindingResult validator,@Nullable@RequestParam Integer page){
        return this.userService.updateUser(id,form,validator, page);
    }
}
