package scm.api.restapi.medium.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import scm.api.restapi.medium.persistence.entiry.Categories;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriesForm {

    public CategoriesForm(Categories c) {
        this.id = c.getId();
        this.name=c.getName();
    }
    private Integer id;
    private String name;
    
}
