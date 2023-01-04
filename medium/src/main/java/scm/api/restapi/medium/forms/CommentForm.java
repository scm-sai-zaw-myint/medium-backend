package scm.api.restapi.medium.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentForm {

    private Integer id;
    
    private String body;
    
    private Integer parentCommentId;
    
}
