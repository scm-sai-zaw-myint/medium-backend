package scm.api.restapi.medium.forms;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import scm.api.restapi.medium.persistence.entiry.Posts;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostForm {

    private Integer id;
    
    private String title;
    
    private String description;
    
    private MultipartFile image;
    
    private String imageURL;
    
    private String categories;
    
    public PostForm(Posts post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.description = post.getDescription();
        this.imageURL = post.getImage();
    }
}
