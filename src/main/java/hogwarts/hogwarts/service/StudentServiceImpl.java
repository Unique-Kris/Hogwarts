package hogwarts.hogwarts.service;

import hogwarts.hogwarts.exception.StudentAlreadyExistsException;
import hogwarts.hogwarts.model.Student;
import hogwarts.hogwarts.repositories.StudentRepository;
import org.springframework.stereotype.Service;


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
        return studentRepository.findById(id).get();
    }

    public Student editStudent(long id, Student student) {
        if (!studentRepository.existsById(id)) {
            throw new StudentAlreadyExistsException();
        }
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }
}
