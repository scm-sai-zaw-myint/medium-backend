package scm.api.restapi.medium.bl.service;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import scm.api.restapi.medium.forms.CommentForm;
import scm.api.restapi.medium.forms.reponse.CommentResponse;

public interface CommentService {

    ResponseEntity<?> postComment(Integer pid, CommentForm form, BindingResult validator);

    ResponseEntity<?> getComment(Integer id);

    ResponseEntity<?> updateComment(Integer pid, Integer id, CommentForm form, BindingResult validator);

    ResponseEntity<?> deleteComment(Integer pid, Integer id);

    public CommentResponse getCommentResponse(Integer pid,Integer id);
}
