package scm.api.restapi.medium.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import scm.api.restapi.medium.persistence.entiry.Categories;

public interface CategoriesRepo extends JpaRepository<Categories, Integer>{

}
