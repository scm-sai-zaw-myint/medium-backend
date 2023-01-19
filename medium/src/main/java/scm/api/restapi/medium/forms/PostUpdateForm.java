package scm.api.restapi.medium.forms;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import scm.api.restapi.medium.persistence.entiry.Posts;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateForm {

    private Integer id;
    
    @NotNull
    @Size(min = 6, max = 100)
    private String title;
    
    @NotNull
    @Size(min = 100, max = 1600)
    private String description;
    
    private MultipartFile image;
    
    private String imageURL;
    
    @NotNull
    @Pattern(regexp = "^[0-9]+(?:,[0-9]+)*$",message="Invalid cateogry")
    private String categories;
    
    public PostUpdateForm(Posts post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.description = post.getDescription();
        this.imageURL = post.getImage();
    }
}
