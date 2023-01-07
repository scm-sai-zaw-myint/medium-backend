package scm.api.restapi.medium.forms;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull
    @Size(min = 1, max = 1000)
    private String body;
    
    private Integer parentCommentId;
    
}
