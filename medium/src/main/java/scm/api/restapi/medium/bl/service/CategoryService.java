package scm.api.restapi.medium.bl.service;

import org.springframework.http.ResponseEntity;

public interface CategoryService {

    ResponseEntity<?> getAllCategories();

    ResponseEntity<?> getUsedCategories();
    
    ResponseEntity<?> getRelatedPosts(String id);

}
