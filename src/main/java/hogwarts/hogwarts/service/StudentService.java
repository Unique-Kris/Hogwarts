package hogwarts.hogwarts.service;

import hogwarts.hogwarts.model.Avatar;
import hogwarts.hogwarts.model.Student;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface StudentService {

    Student addStudent(Student student);

    Student findStudent(long id);

    Student editStudent(long id, Student student);

    void deleteStudent(long id);

    Collection<Student> getAllStudents();

    List<Student> findByAge(int age);

    Avatar findAvatar(long studentId);

    void uploadAvatar(Long studentId, MultipartFile file) throws IOException;

    String getExtension(String fileName);
}