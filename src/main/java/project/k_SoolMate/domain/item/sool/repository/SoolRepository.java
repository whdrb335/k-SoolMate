package project.k_SoolMate.domain.item.sool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.k_SoolMate.domain.item.sool.entity.Sool;

public interface SoolRepository extends JpaRepository<Sool, Long> {
    boolean existsByName(String name);
}
