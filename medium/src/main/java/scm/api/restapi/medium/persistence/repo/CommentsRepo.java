package scm.api.restapi.medium.persistence.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import scm.api.restapi.medium.persistence.entiry.Comments;

public interface CommentsRepo extends JpaRepository<Comments, Integer>{

    @Query(value="SELECT * FROM Comments as c WHERE c.id != ?1 && c.parent_comment_id = ?1",nativeQuery=true)
    public List<Comments> findChildComments(Integer id);
}
