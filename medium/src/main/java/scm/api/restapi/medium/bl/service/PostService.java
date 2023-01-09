package scm.api.restapi.medium.bl.service;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import scm.api.restapi.medium.forms.PostForm;

public interface PostService {

    ResponseEntity<?> createPost(PostForm form, String access_token, BindingResult validator);

    ResponseEntity<?> getPost(Integer id);
    
    ResponseEntity<?> updatePost(Integer id, PostForm form, BindingResult validator);

    ResponseEntity<?> getPosts(Boolean me);

    ResponseEntity<?> deletePost(Integer id);

    ResponseEntity<?> getLatestPosts(Integer limit);

    ResponseEntity<?> searchPost(String search);

}
