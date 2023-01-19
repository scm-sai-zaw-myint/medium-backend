package scm.api.restapi.medium.forms.reponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import scm.api.restapi.medium.common.PropertyUtil;
import scm.api.restapi.medium.forms.CategoriesForm;
import scm.api.restapi.medium.persistence.entiry.Categories;
import scm.api.restapi.medium.persistence.entiry.Posts;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

    private static final String server = "http://localhost:8000/api";
    
    public PostResponse(Posts p) {
        this.id = p.getId();
        this.title = p.getTitle();
        this.description = p.getDescription();
        if(p.getImage() != null)
            this.image = server + "/assets/image/" + p.getImage();
        if(p.getCategories()!=null && p.getCategories().size() > 0) {
            this.putCategories(p.getCategories());
        }
        this.user= new UserResponse(p.getUser());
        this.createdAt = p.getCreatedAt();
        this.updatedAt = p.getUdpatedAt();
        this.time = PropertyUtil.diffforhuman(updatedAt);
    }

    private void putCategories(Set<Categories> categories) {
        List<CategoriesForm> cates = new ArrayList<>();
        for(Categories c:categories) {
            cates.add(new CategoriesForm(c));
        }
        this.setCategories(cates);
    }
    @JsonInclude(Include.NON_NULL)
    private Integer id;
    @JsonInclude(Include.NON_NULL)
    private String title;
    @JsonInclude(Include.NON_NULL)
    private String description;
    @JsonInclude(Include.NON_NULL)
    private String image;
    @JsonInclude(Include.NON_NULL)
    private List<CategoriesForm> categories;
    private List<CommentResponse> comments = new ArrayList<>();
    @JsonInclude(Include.NON_NULL)
    private UserResponse user;
    @JsonInclude(Include.NON_NULL)
    private Date createdAt;
    @JsonInclude(Include.NON_NULL)
    private Date updatedAt;
    @JsonInclude(Include.NON_NULL)
    private String time;
}
