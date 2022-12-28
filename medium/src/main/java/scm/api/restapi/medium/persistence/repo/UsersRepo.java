package scm.api.restapi.medium.persistence.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import scm.api.restapi.medium.persistence.entiry.Users;

public interface UsersRepo extends JpaRepository<Users, Integer> {
    Optional<Users> findByEmail(String email);
}
