package scm.api.restapi.medium.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import scm.api.restapi.medium.bl.service.CategoryService;

@RestController
@CrossOrigin
@RequestMapping("/api/posts/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;
    
    @GetMapping
    public ResponseEntity<?> getCategories(){
        return this.categoryService.getAllCategories();
    }
    
}
