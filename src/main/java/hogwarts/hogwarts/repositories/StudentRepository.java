package hogwarts.hogwarts.repositories;

import hogwarts.hogwarts.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByAge(int age);

    List<Student> findByAgeBetween(int startAge, int endAge);

}