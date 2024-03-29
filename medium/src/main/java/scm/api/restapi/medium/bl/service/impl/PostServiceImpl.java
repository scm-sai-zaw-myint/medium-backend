package scm.api.restapi.medium.bl.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import jakarta.transaction.Transactional;
import scm.api.restapi.medium.bl.service.AuthService;
import scm.api.restapi.medium.bl.service.CommentService;
import scm.api.restapi.medium.bl.service.PostService;
import scm.api.restapi.medium.common.Pagination;
import scm.api.restapi.medium.common.PropertyUtil;
import scm.api.restapi.medium.common.Response;
import scm.api.restapi.medium.common.Validator;
import scm.api.restapi.medium.forms.CategoriesForm;
import scm.api.restapi.medium.forms.PostForm;
import scm.api.restapi.medium.forms.PostUpdateForm;
import scm.api.restapi.medium.forms.reponse.CommentResponse;
import scm.api.restapi.medium.forms.reponse.PostResponse;
import scm.api.restapi.medium.persistence.entiry.Categories;
import scm.api.restapi.medium.persistence.entiry.Comments;
import scm.api.restapi.medium.persistence.entiry.Posts;
import scm.api.restapi.medium.persistence.entiry.Users;
import scm.api.restapi.medium.persistence.repo.CategoriesRepo;
import scm.api.restapi.medium.persistence.repo.CommentsRepo;
import scm.api.restapi.medium.persistence.repo.PostsRepo;
import scm.api.restapi.medium.persistence.repo.UsersRepo;

@Transactional
@Service
public class PostServiceImpl implements PostService{

    @Autowired
    PostsRepo postsRepo;
    
    @Autowired
    CategoriesRepo categoriesRepo;
    
    @Autowired
    AuthService authService;
    
    @Autowired
    CommentService commentService;
    
    @Autowired
    PropertyUtil propertyUtil;
    
    @Autowired
    CommentsRepo commentRepo;
    
    @Value("${image.upload-dir}")
    private String imageStorageDIR;
    
    @Value("${app.pagination.limit}")
    private Integer limit;
    
    @SuppressWarnings({ "deprecation", "unchecked" })
    @Override
    public ResponseEntity<?> createPost(PostForm form, String access_token, BindingResult validator) {
        if(validator.hasErrors()) return Response.send(HttpStatus.BAD_REQUEST, false, "Bad request!", Validator.parseErrorMessage(validator), null, null);
        if(!this.validateForm(form)) {
            return Response.send(HttpStatus.BAD_REQUEST, false, "Missing required fields!",null, null, null);
        }
        if(form.getImage() != null ) {
            try {
                form.setImageURL(this.propertyUtil.uploadPhotorequest(form.getImage(), form.getTitle(), false));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        Posts post = new Posts(form);
        Set<Categories> categories = new HashSet<>();
        if(form.getCategories() != null) {
            List<Object> ints =  (List<Object>) this.propertyUtil.convertStringToList(form.getCategories(), ",");
            for(Object cid: ints) {
                Integer cateId = cid instanceof String ? Integer.parseInt((String) cid) : cid instanceof Integer ? (Integer) cid :  (Integer) cid;
                Categories cat = this.categoriesRepo.getById(cateId);
                categories.add(cat);
            }
        }
        Users user = this.authService.authUser(null);
        post.setUser(user);
        post.setCategories(categories);
        Posts created = this.postsRepo.save(post);
        PostResponse response = new PostResponse(created);
        List<CategoriesForm> catList = new ArrayList<>();

        for(Categories c:created.getCategories()) {
            catList.add(new CategoriesForm(c));
        }
        response.setCategories(catList);
        return Response.send(HttpStatus.CREATED, true, "Create post success!", response, null, null);
    }

    @SuppressWarnings({ "deprecation", "unchecked" })
    @Override
    public ResponseEntity<?> updatePost(Integer id, PostUpdateForm form, BindingResult validator) {
        if(validator.hasErrors()) return Response.send(HttpStatus.BAD_REQUEST, false, "Bad request!", Validator.parseErrorMessage(validator), null, null);
        if(!this.postsRepo.existsById(id)) return Response.send(HttpStatus.BAD_REQUEST, false, "No post found!", null, null, null);
        Posts post = this.postsRepo.getById(id);
        if(form.getTitle() != null) post.setTitle(form.getTitle());
        if(form.getCategories() != null) {
            List<Object> ints =  (List<Object>) this.propertyUtil.convertStringToList(form.getCategories(), ",");
            Set<Categories> categories = new HashSet<>();
            for(Object cid: ints) {
                Integer cateId = cid instanceof String ? Integer.parseInt((String) cid) : cid instanceof Integer ? (Integer) cid :  (Integer) cid;
                Categories cat = this.categoriesRepo.getById(cateId);
                categories.add(cat);
            }
            post.setCategories(categories);
        }
        if(form.getDescription() != null) post.setDescription(form.getDescription());
        try {
            if(form.getImage() != null && form.getImage().getBytes().length > 0) {
                if(post.getImage()!=null) {
                    this.checkImage(this.imageStorageDIR+File.separator+post.getImage());
                }
                post.setImage(this.propertyUtil.uploadPhotorequest(form.getImage(), form.getTitle(), false));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        PostResponse response = new PostResponse(this.postsRepo.save(post));
        return Response.send(HttpStatus.ACCEPTED, true, "Update post success", response, null, null);
    }

    @SuppressWarnings("deprecation")
    @Override
    public ResponseEntity<?> getPost(Integer id) {
        if(!this.postsRepo.existsById(id)) return Response.send(HttpStatus.BAD_REQUEST, false, "No post found!", null, null, null);
        Posts post = this.postsRepo.getById(id);
        
        PostResponse response = new PostResponse(post);
        if(post.getComments() != null && !post.getComments().isEmpty()) {
            List<CommentResponse> comList = new ArrayList<>();
            for(Comments com:post.getComments()) {
                comList.add(this.commentService.getCommentResponse(post.getId(), com.getId()));
            }
            response.setComments(comList);
        }
        
        return Response.send(HttpStatus.OK, true, "Get post data success.",response, null, null);
    }

    @Override
    public ResponseEntity<?> getPosts(Boolean me, Integer page) {
        
        List<PostResponse> postList = new ArrayList<>();
        if(me != null && me) {
            Users user = this.authService.authUser(null);
            for(Posts p: user.getPosts()) {
                postList.add(new PostResponse(p));
            }
        }else {
            for(Posts p: this.postsRepo.findAll()) {
                postList.add(new PostResponse(p));
            }
        }
        page = page == null ? 1 : page;
        Pagination pagination = new Pagination(postList, page, limit,"posts");
        return Response.send(HttpStatus.OK, true, me != null && me ? "Get user post success":"Get all post success", pagination.getData(), null, pagination);
    }

    @SuppressWarnings("deprecation")
    @Override
    public ResponseEntity<?> deletePost(Integer id) {
        if(!this.postsRepo.existsById(id)) return Response.send(HttpStatus.BAD_REQUEST, false, "No post found!", null, null, null);
        Posts post = this.postsRepo.getById(id);
        List<Categories> cat = this.categoriesRepo.findAll();
        for(Categories c:cat) {
            c.getPosts().remove(post);
        }
        for(Comments com:post.getComments()) {
            this.commentService.deleteComment(post.getId(), com.getId());
        }
        this.postsRepo.delete(post);
        return Response.send(HttpStatus.ACCEPTED, true, "Delete Post success.", null, null, null);
    }

    @Override
    public ResponseEntity<?> getLatestPosts(Integer limit) {
        limit = limit == null ? 3 : limit;
        List<Posts> latest = this.postsRepo.getLatestPosts(limit);
        List<PostResponse> responseList = new ArrayList<>();
        for(Posts p:latest) {
            responseList.add(new PostResponse(p));
        }
        return Response.send(HttpStatus.OK, true, "Get latest posts success.", responseList, null, null);
    }

    @Override
    public ResponseEntity<?> searchPost(String search, Integer page) {
        List<Posts> posts = this.postsRepo.searchPosts(search);
        List<PostResponse> responseList = new ArrayList<>();
        for(Posts p:posts) {
            responseList.add(new PostResponse(p));
        }
        page = page == null ? 1 : page;
        Pagination pagination = new Pagination(responseList, page, limit,"posts");
        
        return Response.send(HttpStatus.OK, true, "Search posts success.", pagination.getData(), null, pagination);
    }

    private boolean validateForm(PostForm form) {
        return form.getTitle() != null && form.getDescription() != null && form.getCategories() != null;
    }

    private boolean checkImage(String dir) {
        File f = new File(dir);
        return f.delete();
    }
}
