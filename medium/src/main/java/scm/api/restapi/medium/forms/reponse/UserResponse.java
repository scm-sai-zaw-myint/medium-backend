package scm.api.restapi.medium.forms.reponse;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import scm.api.restapi.medium.persistence.entiry.Users;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Integer id;

    private String name;

    private String email;

    private String bio;

    private String profileURL;

    private Date createdAt;

    private Date updatedAt;
    
    public UserResponse(Users user) {
        this.id = user.getId();
        this.name = user.getName();
        this.bio = user.getBio();
        this.email = user.getEmail();
        this.profileURL = user.getProfile();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }
}
