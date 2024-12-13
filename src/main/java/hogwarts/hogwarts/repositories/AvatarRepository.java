package hogwarts.hogwarts.repositories;

import hogwarts.hogwarts.model.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {

Optional<Avatar> findByStudentId(Long studentId);

Page<Avatar> findAll(Pageable pageable);
}