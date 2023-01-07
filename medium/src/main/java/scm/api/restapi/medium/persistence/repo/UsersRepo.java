package scm.api.restapi.medium.persistence.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import scm.api.restapi.medium.persistence.entiry.Users;

public interface UsersRepo extends JpaRepository<Users, Integer> {
    Optional<Users> findByEmail(String email);
    
    @Query(value="SELECT * FROM Users as u WHERE u.username = ?1 OR u.email = ?2",nativeQuery = true)
    Users getUserBynameOrEmail(String username,String email);
}
