package scm.api.restapi.medium.forms.reponse;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import scm.api.restapi.medium.forms.CategoriesForm;
import scm.api.restapi.medium.persistence.entiry.Posts;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

    public PostResponse(Posts p) {
        this.id = p.getId();
        this.title = p.getTitle();
        this.derscription = p.getDescription();
        this.image = p.getImage();
    }

    private Integer id;
    
    private String title;
    
    private String derscription;
    
    private String image;
    
    private List<CategoriesForm> categories;
    
}
