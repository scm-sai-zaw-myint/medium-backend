package scm.api.restapi.medium.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.common.lang.Nullable;
import scm.api.restapi.medium.bl.service.PostService;
import scm.api.restapi.medium.forms.PostForm;

@CrossOrigin
@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    PostService postService;
    
    @GetMapping
    public ResponseEntity<?> getPosts(@Nullable@RequestParam Boolean me){
        return this.postService.getPosts(me);
    }
    
    @GetMapping("/latest")
    public ResponseEntity<?> getLatestPosts(@Nullable@RequestParam Integer limit){
        return this.postService.getLatestPosts(limit);
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> createPost(@ModelAttribute PostForm form,@Nullable@RequestParam String access_token){
        return this.postService.createPost(form,access_token);
    }
    
    @GetMapping(value="/{id}")
    public ResponseEntity<?> getPost(@PathVariable Integer id){
        return this.postService.getPost(id);
    }
    
    @PutMapping(value="/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> updatePost(@PathVariable Integer id,@ModelAttribute PostForm form){
        return this.postService.updatePost(id, form);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Integer id){
        return this.postService.deletePost(id);
    }
}
