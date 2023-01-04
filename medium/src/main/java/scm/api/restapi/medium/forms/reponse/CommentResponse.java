package scm.api.restapi.medium.forms.reponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scm.api.restapi.medium.persistence.entiry.Comments;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {

    @JsonInclude(Include.NON_NULL)
    private Integer id;

    private Integer parentCommentId;

    @JsonInclude(Include.NON_NULL)
    private String body;
    
    private List<CommentResponse> childComments = new ArrayList<>();
    
    private UserResponse user;
    
    private Date createdAt;
    
    private Date updatedAt;
    
    public CommentResponse(Comments com) {
        this.id = com.getId();
        this.parentCommentId = com.getParentCommentId();
        this.body = com.getBody();
        this.user = new UserResponse(com.getUser());
        this.createdAt = com.getCreatedAt();
        this.updatedAt = com.getUdpatedAt();
    }
    
}
