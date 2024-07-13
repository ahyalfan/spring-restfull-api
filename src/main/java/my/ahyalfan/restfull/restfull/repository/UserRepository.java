package my.ahyalfan.restfull.restfull.repository;

import my.ahyalfan.restfull.restfull.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findFirstByToken(String token);
}