package scm.api.restapi.medium.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import scm.api.restapi.medium.persistence.entiry.Categories;

public interface CategoriesRepo extends JpaRepository<Categories, Integer>{

    @Query(value="SELECT * FROM Categories as c WHERE c.name = :name LIMIT 1",nativeQuery = true)
    public Categories getCategoryByName(String name);
    
}
