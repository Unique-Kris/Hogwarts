package hogwarts.hogwarts.service;

import hogwarts.hogwarts.exception.NotFoundStudentException;
import hogwarts.hogwarts.model.Student;
import hogwarts.hogwarts.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        return studentRepository.findById(id).orElseThrow(NotFoundStudentException::new);
    }

    public Student editStudent(long id, Student student) {
        Student exist = studentRepository.findById(id).orElseThrow(NotFoundStudentException::new);
        exist.setName(student.getName());
        exist.setAge(student.getAge());
        return studentRepository.save(exist);
    }

    public void deleteStudent(long id) {
        studentRepository.findById(id).orElseThrow(NotFoundStudentException::new);
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Collection<Student> findByAgeBetween(int startAge, int endAge) {
        return studentRepository.findByAgeBetween(startAge, endAge);
    }
}