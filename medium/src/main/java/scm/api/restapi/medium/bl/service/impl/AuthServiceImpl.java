package scm.api.restapi.medium.bl.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import scm.api.restapi.medium.bl.service.AuthService;
import scm.api.restapi.medium.common.PropertyUtil;
import scm.api.restapi.medium.common.Response;
import scm.api.restapi.medium.forms.UserForm;
import scm.api.restapi.medium.forms.reponse.AuthResponseForm;
import scm.api.restapi.medium.forms.reponse.PostResponse;
import scm.api.restapi.medium.forms.reponse.UserResponse;
import scm.api.restapi.medium.forms.request.AuthRequestForm;
import scm.api.restapi.medium.jwt.JWTUtil;
import scm.api.restapi.medium.persistence.entiry.Posts;
import scm.api.restapi.medium.persistence.entiry.Users;
import scm.api.restapi.medium.persistence.repo.UsersRepo;

@Transactional
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
    
    @Value("${image.upload-dir}")
    private String fileStorageDIR;
    
    @Autowired
    PropertyUtil propertyUtil;
    
    @Override
    public ResponseEntity<?> login(AuthRequestForm form, String access_token) {
        if(form == null) return  Response.send(HttpStatus.BAD_REQUEST, false, "Request body required!", null, null);
        if(form.getEmail() == null || form.getPassword() == null) return Response.send(HttpStatus.BAD_REQUEST, false, "Email or password required!", null, null);
        try {
            Authentication authentication = authManager
                    .authenticate(new UsernamePasswordAuthenticationToken(form.getEmail(), form.getPassword()));

            Users user = (Users) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(user);
            AuthResponseForm response = new AuthResponseForm(user.getEmail(), accessToken,new Date(System.currentTimeMillis() + (EXPIRE_DURATION * 60) * 1000));
            return Response.send(HttpStatus.ACCEPTED, true, "Login success!", response, null);

        } catch (BadCredentialsException ex) {
            ex.printStackTrace();
            Map<String, Object> error = new HashMap<>();
            error.put("error", ex.getMessage());
            return Response.send(HttpStatus.BAD_REQUEST, false, "Login fail!",error, null);
        }
    }

    @Override
    public ResponseEntity<?> registration(UserForm form, String access_token) {
        if(form.getProfile() != null) {
            try {
                form.setProfileURL(this.propertyUtil.uploadPhotorequest(form.getProfile(), form.getEmail(), true));
            } catch (IOException e) {
                e.printStackTrace();
                return Response.send(HttpStatus.INTERNAL_SERVER_ERROR, false, e.getMessage(), e, null);
            }
        }
        Users user = new Users(form);
        try {
            Users savedUser = usersRepo.save(user);
            return Response.send(HttpStatus.CREATED,true, "Registration success!",savedUser, null);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.send(HttpStatus.BAD_REQUEST, false, e.getMessage(), e, null);
        }
    }
    
    @SuppressWarnings("deprecation")
    @Override
    public Users authUser(String token) {
        UserDetails userDetails = token == null
                ? (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()
                : this.jwtUtil.getUserDetails(token);
        
        return this.usersRepo.getById(((Users) userDetails).getId());
    }

    @Override
    public ResponseEntity<?> getUserInfo() {
        Users user = this.authUser(null);
        UserResponse response = new UserResponse(user);
        if(user.getPosts() != null) {
            Set<PostResponse> list = new HashSet<>();
            for(Posts p:user.getPosts()) {
                list.add(new PostResponse(p));
            }
            response.setPosts(list);
        }
        return Response.send(HttpStatus.OK, true, "Get user success", response, null);
    }

}
