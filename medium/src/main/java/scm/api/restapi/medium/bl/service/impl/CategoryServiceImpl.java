package scm.api.restapi.medium.bl.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import scm.api.restapi.medium.bl.service.CategoryService;
import scm.api.restapi.medium.common.Response;
import scm.api.restapi.medium.forms.reponse.CategoryResponse;
import scm.api.restapi.medium.forms.reponse.PostResponse;
import scm.api.restapi.medium.persistence.entiry.Categories;
import scm.api.restapi.medium.persistence.entiry.Posts;
import scm.api.restapi.medium.persistence.repo.CategoriesRepo;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoriesRepo categoriesRepo;

    @Override
    public ResponseEntity<?> getAllCategories() {
        List<Categories> cat = this.categoriesRepo.findAll();
        List<CategoryResponse> responseList = new ArrayList<>();
        for(Categories c:cat) {
            responseList.add(new CategoryResponse(c.getId(), c.getName()));
        }
        return Response.send(HttpStatus.OK, true, "Get categories success", responseList, null, null);
    }

    @Override
    public ResponseEntity<?> getUsedCategories() {
        List<Categories> cat = this.categoriesRepo.getAllUsedCategories();
        List<CategoryResponse> responseList = new ArrayList<>();
        for(Categories c:cat) {
            responseList.add(new CategoryResponse(c.getId(), c.getName()));
        }
        return Response.send(HttpStatus.OK, true, "Get categories success", responseList, null, null);
    }

    @Override
    public ResponseEntity<?> getRelatedPosts(String name) {
        Categories category = this.categoriesRepo.getCategoryByName(name);
        if(category == null) return Response.send(HttpStatus.BAD_REQUEST, false, "No category match", category, null, null);
        Set<Posts> posts = category.getPosts();
        List<PostResponse> response = new ArrayList<>();
        for(Posts p :posts) {
            response.add(new PostResponse(p));
        }
        return Response.send(HttpStatus.OK, true, "Get related posts success.", response, null, null);
    }
    
}
