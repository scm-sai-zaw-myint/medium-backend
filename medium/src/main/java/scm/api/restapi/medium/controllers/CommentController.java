package scm.api.restapi.medium.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import scm.api.restapi.medium.bl.service.CommentService;
import scm.api.restapi.medium.forms.CommentForm;

@RestController
@RequestMapping("api/{pid}/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;
    
    @GetMapping("")
    public ResponseEntity<?> getComments(@PathVariable Integer pid){
        return this.commentService.getComments();
    }

    @PostMapping("")
    public ResponseEntity<?> postComment(@PathVariable Integer pid,@RequestBody CommentForm form){
        return this.commentService.postComment(pid,form);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getComment(@PathVariable Integer pid,@PathVariable Integer id){
        return this.commentService.getComment(id);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Integer pid,@PathVariable Integer id,@RequestBody CommentForm form){
        return this.commentService.updateComment(pid,id,form);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer pid,@PathVariable Integer id){
        return this.commentService.deleteComment(pid,id);
    }
}
