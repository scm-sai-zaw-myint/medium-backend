package scm.api.restapi.medium.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import scm.api.restapi.medium.persistence.entiry.Posts;

public interface PostsRepo extends JpaRepository<Posts, Integer>{

}
