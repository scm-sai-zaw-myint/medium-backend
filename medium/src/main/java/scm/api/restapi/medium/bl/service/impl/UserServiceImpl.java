package scm.api.restapi.medium.bl.service.impl;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import scm.api.restapi.medium.bl.service.UserService;
import scm.api.restapi.medium.common.PropertyUtil;
import scm.api.restapi.medium.common.Response;
import scm.api.restapi.medium.forms.UserForm;
import scm.api.restapi.medium.forms.reponse.UserResponse;
import scm.api.restapi.medium.persistence.entiry.Users;
import scm.api.restapi.medium.persistence.repo.UsersRepo;

@Transactional
@Service
public class UserServiceImpl implements UserService{
    
    @Autowired
    private PropertyUtil propertyUtil;

    @Autowired
    private UsersRepo usersRepo;
    
    @Value("${profile.upload-dir}")
    private String prfileStorageDIR;
    
    @SuppressWarnings("deprecation")
    @Override
    public ResponseEntity<?> getUserById(Integer id) {
        Users user = this.usersRepo.getById(id);
        if(user == null) return Response.send(HttpStatus.NO_CONTENT, false, "Success with empty data", user, null);
        return Response.send(HttpStatus.ACCEPTED, true, "Get user data success", new UserResponse(user), null);
    }

    @SuppressWarnings("deprecation")
    @Override
    public ResponseEntity<?> updateUser(Integer id, UserForm form) {
        Users user = this.usersRepo.getById(id);
        if(user == null) return Response.send(HttpStatus.NO_CONTENT, false, "Success with empty data", user, null);
        if(form.getName() != null) user.setUsername(form.getName());
        if(form.getBio() != null) user.setBio(form.getBio());
        if(form.getEmail() != null) user.setEmail(form.getEmail());
        if(form.getProfile() != null) {
            try {
                this.checkProfile(this.prfileStorageDIR+File.separator+user.getProfile());
                
                user.setProfile(this.propertyUtil.uploadPhotorequest(
                        form.getProfile(), 
                        form.getEmail() == null ? user.getEmail():form.getEmail(), 
                                true));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        UserResponse userResponse = new UserResponse(this.usersRepo.save(user));
        return Response.send(HttpStatus.ACCEPTED, true, "update User success", userResponse, null);
    }

    private boolean checkProfile(String dir) {
        File f = new File(dir);
        return f.delete();
    }
}
