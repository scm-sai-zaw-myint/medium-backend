package scm.api.restapi.medium.persistence.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import scm.api.restapi.medium.persistence.entiry.Categories;

public interface CategoriesRepo extends JpaRepository<Categories, Integer>{

    @Query(value="SELECT * FROM Categories as c WHERE c.name = :name LIMIT 1",nativeQuery = true)
    public Categories getCategoryByName(String name);
    
    @Query(value = "SELECT * FROM categories as c WHERE c.id in (SELECT pc.category_id FROM post_categories as pc)", nativeQuery = true)
    public List<Categories> getAllUsedCategories();
}
