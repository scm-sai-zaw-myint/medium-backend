package scm.api.restapi.medium.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.common.lang.Nullable;
import jakarta.servlet.http.HttpSession;
import scm.api.restapi.medium.forms.UserForm;
import scm.api.restapi.medium.forms.request.AuthRequestForm;
import scm.api.restapi.medium.service.AuthService;

@RestController
@RequestMapping("/api/auth/")
@CrossOrigin
public class AuthController {

    @Autowired
    AuthService authService;
    
    @Autowired
    HttpSession session;
    
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody AuthRequestForm form,@Nullable @RequestParam String access_token){
        return authService.login(form,access_token);
    }
    
    @RequestMapping(value = "registration", method = RequestMethod.POST, consumes = { "multipart/form-data" })
    public ResponseEntity<?> registration(@ModelAttribute UserForm form,@Nullable @RequestParam String access_token){
        return authService.registration(form,access_token);
    }
    
}
