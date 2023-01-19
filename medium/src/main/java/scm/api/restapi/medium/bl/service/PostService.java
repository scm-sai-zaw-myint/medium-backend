package scm.api.restapi.medium.bl.service;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import scm.api.restapi.medium.forms.PostForm;
import scm.api.restapi.medium.forms.PostUpdateForm;

public interface PostService {

    ResponseEntity<?> createPost(PostForm form, String access_token, BindingResult validator);

    ResponseEntity<?> getPost(Integer id);
    
    ResponseEntity<?> updatePost(Integer id, PostUpdateForm form, BindingResult validator);

    ResponseEntity<?> getPosts(Boolean me, Integer page);

    ResponseEntity<?> deletePost(Integer id);

    ResponseEntity<?> getLatestPosts(Integer limit);

    ResponseEntity<?> searchPost(String search, Integer page);

}
