package scm.api.restapi.medium.service;

import org.springframework.http.ResponseEntity;

import scm.api.restapi.medium.forms.PostForm;

public interface PostService {

    ResponseEntity<?> createPost(PostForm form, String access_token);

    ResponseEntity<?> getPost(Integer id);
    
    ResponseEntity<?> updatePost(Integer id, PostForm form);

    ResponseEntity<?> getPosts(Boolean me);

}
