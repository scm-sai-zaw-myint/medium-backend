package scm.api.restapi.medium.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import scm.api.restapi.medium.common.Response;
import scm.api.restapi.medium.forms.UserForm;
import scm.api.restapi.medium.forms.reponse.AuthResponseForm;
import scm.api.restapi.medium.forms.request.AuthRequestForm;
import scm.api.restapi.medium.jwt.JWTUtil;
import scm.api.restapi.medium.persistence.entiry.Users;
import scm.api.restapi.medium.persistence.repo.UsersRepo;
import scm.api.restapi.medium.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    AuthenticationManager authManager;
    
    @Autowired
    private JWTUtil jwtUtil;
    
    @Autowired
    private UsersRepo usersRepo;
    
    @Value("${app.jwt.validity}")
    private long EXPIRE_DURATION;
    
    @Override
    public ResponseEntity<?> login(AuthRequestForm form, String access_token) {
        if(form == null) return  Response.send(HttpStatus.BAD_REQUEST, false, "Request body required!", null);
        if(form.getEmail() == null || form.getPassword() == null) return Response.send(HttpStatus.BAD_REQUEST, false, "Email or password required!", null);
        try {
            Authentication authentication = authManager
                    .authenticate(new UsernamePasswordAuthenticationToken(form.getEmail(), form.getPassword()));

            Users user = (Users) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(user);
            AuthResponseForm response = new AuthResponseForm(user.getEmail(), accessToken,new Date(System.currentTimeMillis() + (EXPIRE_DURATION * 60) * 1000));
            return Response.send(HttpStatus.ACCEPTED, true, "Login success!", response);

        } catch (BadCredentialsException ex) {
            ex.printStackTrace();
            Map<String, Object> error = new HashMap<>();
            error.put("error", ex.getMessage());
            return Response.send(HttpStatus.BAD_REQUEST, false, "Login fail!",error);
        }
    }

    @Override
    public ResponseEntity<?> registration(UserForm form, String access_token) {
        Users user = new Users(form);
        
        try {
            Users savedUser = usersRepo.save(user);
            return Response.send(HttpStatus.CREATED,true, "Registration success!",savedUser);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.send(HttpStatus.BAD_REQUEST, false, e.getMessage(), e);
        }
    }

}
