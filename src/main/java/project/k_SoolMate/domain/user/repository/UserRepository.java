package project.k_SoolMate.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.k_SoolMate.domain.user.User;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByLoginId(String loginId);
}
