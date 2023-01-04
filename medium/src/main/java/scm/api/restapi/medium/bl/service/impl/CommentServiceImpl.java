package scm.api.restapi.medium.bl.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import scm.api.restapi.medium.bl.service.AuthService;
import scm.api.restapi.medium.bl.service.CommentService;
import scm.api.restapi.medium.common.Response;
import scm.api.restapi.medium.forms.CommentForm;
import scm.api.restapi.medium.forms.reponse.CommentResponse;
import scm.api.restapi.medium.persistence.entiry.Comments;
import scm.api.restapi.medium.persistence.entiry.Posts;
import scm.api.restapi.medium.persistence.entiry.Users;
import scm.api.restapi.medium.persistence.repo.CommentsRepo;
import scm.api.restapi.medium.persistence.repo.PostsRepo;

@Transactional
@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private AuthService authService;
    
    @Autowired
    private CommentsRepo commentsRepo;
    
    @Autowired
    private PostsRepo postsRepo;
    
    private Integer deleted = 0;
    
    @Override
    public ResponseEntity<?> getComments() {
        
        return null;
    }

    @SuppressWarnings("deprecation")
    @Override
    public ResponseEntity<?> postComment(Integer pid, CommentForm form) {
        if(!this.validatePost(pid)) return Response.send(HttpStatus.NOT_FOUND, false, "No post found!", null, null);
        if(!this.validateCommentParent(form.getParentCommentId())) 
            return Response.send(HttpStatus.BAD_REQUEST, false, "Invalid reply! No parent comment found!", null, null);
        form.setId(null);
        Users user = this.authService.authUser(null);
        Posts post = this.postsRepo.getById(pid);
        
        Comments comment = new Comments(form);
        comment.setPost(post);
        comment.setUser(user);
        Comments saved = this.commentsRepo.save(comment);
        
        return Response.send(HttpStatus.OK, true, "Post comment success.", new CommentResponse(saved), null);
    }

    @SuppressWarnings("deprecation")
    @Override
    public ResponseEntity<?> getComment(Integer id) {
        if(!this.commentsRepo.existsById(id)) return Response.send(HttpStatus.BAD_REQUEST, false, "No comment found!", null, null);
        Comments com = this.commentsRepo.getById(id);
        return Response.send(HttpStatus.OK, true, "Get comment success.", this.getCommentResponse(com), null);
    }

    @SuppressWarnings("deprecation")
    @Override
    public ResponseEntity<?> updateComment(Integer pid, Integer id, CommentForm form) {
        if(!this.validatePost(pid)) return Response.send(HttpStatus.NOT_FOUND, false, "No post found!", null, null);
        if(!this.commentsRepo.existsById(id)) return Response.send(HttpStatus.BAD_REQUEST, false, "No comment found!", null, null);
        
        form.setId(null);
        Comments com = this.commentsRepo.getById(id);
        if(form.getBody()!=null) com.setBody(form.getBody());
        Comments updated = this.commentsRepo.save(com);
        return Response.send(HttpStatus.ACCEPTED, true, "Update Comment success.", this.getCommentResponse(updated), null);
    }

    @Override
    public ResponseEntity<?> deleteComment(Integer pid, Integer id) {
        if(!this.validatePost(pid)) return Response.send(HttpStatus.NOT_FOUND, false, "No post found!", null, null);
        if(!this.commentsRepo.existsById(id)) return Response.send(HttpStatus.BAD_REQUEST, false, "No comment found!", null, null);
        this.deleted = 0;
        Integer count = this.deleteHierarchicalComment(id);
        return Response.send(HttpStatus.ACCEPTED, true, "Total "+count+" comments deleted.", null, null);
    }

    @SuppressWarnings("deprecation")
    @Override
    public CommentResponse getCommentResponse(Integer pid,Integer id) {
        if(!this.validatePost(pid) || !this.commentsRepo.existsById(id)) return null;
        Comments com = this.commentsRepo.getById(id);
        return this.getCommentResponse(com);
    }

    //recursive function
    @SuppressWarnings("deprecation")
    private Integer deleteHierarchicalComment(Integer id) {
        List<Comments> finalList = this.commentsRepo.findChildComments(id);
        Comments com = this.commentsRepo.getById(id);
        this.commentsRepo.delete(com);
        this.deleted ++;
        
        for(Comments c:finalList) {
            Integer nid = c.getId();
            this.commentsRepo.delete(c);
            this.deleted ++;
            
            if(this.commentsRepo.findChildComments(nid).size() > 0) {
                //recursive action
                this.deleteHierarchicalComment(nid);
            }
        }
        return this.deleted;
    }

    private CommentResponse getCommentResponse(Comments com) {
        CommentResponse response = new CommentResponse(com);
        List<CommentResponse> getChildComments = this.getChildComments(com.getId());
        response.setChildComments(getChildComments);
        return response;
    }
    
    private List<CommentResponse> getChildComments(Integer id) {
        List<Comments> com = this.commentsRepo.findChildComments(id);
        List<CommentResponse> response = new ArrayList<>();
        for(Comments c:com) {
            response.add(new CommentResponse(c));
        }
        return response;
    }

    private boolean validatePost(Integer pid) {
        return this.postsRepo.existsById(pid);
    }
    
    private boolean validateCommentParent(Integer parentId) {
        if(parentId != null) {
            return this.commentsRepo.existsById(parentId);
        }
        return true;
    }
    
}
