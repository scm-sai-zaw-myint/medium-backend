package scm.api.restapi.medium.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.micrometer.common.lang.Nullable;
import scm.api.restapi.medium.bl.service.CategoryService;
import scm.api.restapi.medium.forms.CategoriesForm;

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

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoriesForm categoryDto){
        return this.categoryService.createCategory(categoryDto);
    }

    @GetMapping("/used")
    public ResponseEntity<?> getUsedCategories(){
        return this.categoryService.getUsedCategories();
    }
    
    @GetMapping("/{name}/posts")
    public ResponseEntity<?> relatedPosts(@PathVariable String name,@Nullable@RequestParam Integer page){
        return this.categoryService.getRelatedPosts(name, page);
    }
    
}
