package scm.api.restapi.medium.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.common.lang.Nullable;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import scm.api.restapi.medium.bl.service.AuthService;
import scm.api.restapi.medium.forms.PasswordForm;
import scm.api.restapi.medium.forms.UserForm;
import scm.api.restapi.medium.forms.request.AuthRequestForm;

@RestController
@RequestMapping("/api/auth/")
@CrossOrigin
public class AuthController {

    @Autowired
    AuthService authService;
    
    @Autowired
    HttpSession session;
    
    @PostMapping("/request/login")
    public ResponseEntity<?> login(@Valid@RequestBody AuthRequestForm form,BindingResult validator,@Nullable @RequestParam String access_token){
        return authService.login(form,access_token,validator);
    }
    
    @RequestMapping(value = "/request/registration", method = RequestMethod.POST, consumes = { "multipart/form-data" })
    public ResponseEntity<?> registration(@Valid @ModelAttribute UserForm form,BindingResult validator,@Nullable @RequestParam String access_token){
        return authService.registration(form,access_token, validator);
    }
    
    @PostMapping("/changepassword")
    public ResponseEntity<?> changePassword(@Valid@RequestBody PasswordForm form,BindingResult validator,@Nullable @RequestParam String access_token){
        return this.authService.changePassword(form,access_token,validator);
    }
    
    @GetMapping("")
    public ResponseEntity<?> getLoggedUserInfo(){
        return this.authService.getUserInfo();
    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        return this.authService.logout();
    }
    
}
