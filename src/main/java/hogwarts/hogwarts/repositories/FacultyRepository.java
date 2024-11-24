package hogwarts.hogwarts.repositories;

import hogwarts.hogwarts.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
}