package scm.api.restapi.medium.bl.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import scm.api.restapi.medium.bl.service.CategoryService;
import scm.api.restapi.medium.common.Response;
import scm.api.restapi.medium.forms.reponse.CategoryResponse;
import scm.api.restapi.medium.persistence.entiry.Categories;
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
        return Response.send(HttpStatus.OK, true, "Get categories success", responseList, null);
    }
    
}
