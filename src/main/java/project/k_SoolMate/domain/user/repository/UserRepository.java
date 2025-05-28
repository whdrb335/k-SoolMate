package project.k_SoolMate.domain.user.repository;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import project.k_SoolMate.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByLoginId(String loginId);

    Optional<User> findByLoginId(String loginId);
}
