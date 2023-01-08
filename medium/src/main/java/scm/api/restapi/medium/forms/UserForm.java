package scm.api.restapi.medium.forms;

import java.util.Date;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scm.api.restapi.medium.persistence.entiry.Users;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class UserForm {

    final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    private Integer id;

    @NotNull
    @NotEmpty
    @Size(min = 6, max = 30)
    private String name;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    private String bio;

    @NotNull
    @NotEmpty
    @Size(min = 6, max = 16)
    private String password;
    
    private MultipartFile profile;

    String profileURL;

    private Date createdAt;

    private Date updatedAt;

    public UserForm(Users user) {
        this.id = user.getId();
        this.name = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.bio = user.getBio();
        this.profileURL = user.getProfile();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }
    
    public String getPassword() {
        return passwordEncoder.encode(this.password);
    }
}
