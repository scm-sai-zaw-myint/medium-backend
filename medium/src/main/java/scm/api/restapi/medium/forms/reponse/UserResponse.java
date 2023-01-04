package scm.api.restapi.medium.forms.reponse;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import scm.api.restapi.medium.persistence.entiry.Users;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private static final String server = "http://localhost:8000/api";
    
    @JsonInclude(Include.NON_NULL)
    private Integer id;
    @JsonInclude(Include.NON_NULL)
    private String name;

    @JsonInclude(Include.NON_NULL)
    private String email;

    @JsonInclude(Include.NON_NULL)
    private String bio;

    @JsonInclude(Include.NON_NULL)
    private String profileURL;

    @JsonInclude(Include.NON_NULL)
    private Date createdAt;

    @JsonInclude(Include.NON_NULL)
    private Date updatedAt;

    @JsonInclude(Include.NON_NULL)
    private Set<PostResponse> posts;
    
    public UserResponse(Users user) {
        this.id = user.getId();
        this.name = user.getUsername();
        this.bio = user.getBio();
        this.email = user.getEmail();
        this.profileURL = server + "/assets/profile/" + user.getProfile();
    }
}
