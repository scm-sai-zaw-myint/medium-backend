package scm.api.restapi.medium.bl.service;

import org.springframework.http.ResponseEntity;
import scm.api.restapi.medium.forms.CategoriesForm;

public interface CategoryService {

    ResponseEntity<?> getAllCategories();

    ResponseEntity<?> getUsedCategories();
    
    ResponseEntity<?> getRelatedPosts(String id, Integer page);

    ResponseEntity<?> createCategory(CategoriesForm categoryDto);
}
