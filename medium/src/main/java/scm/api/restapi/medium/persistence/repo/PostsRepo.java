package scm.api.restapi.medium.persistence.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import scm.api.restapi.medium.persistence.entiry.Posts;

public interface PostsRepo extends JpaRepository<Posts, Integer>{

    @Query(value = "SELECT * FROM Posts as p ORDER BY p.created_at DESC LIMIT ?1",nativeQuery = true)
    List<Posts> getLatestPosts(Integer limit);
    
}
