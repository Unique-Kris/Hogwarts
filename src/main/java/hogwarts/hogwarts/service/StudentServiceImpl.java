package hogwarts.hogwarts.service;

import hogwarts.hogwarts.exception.NotFoundStudentException;
import hogwarts.hogwarts.model.Student;
import hogwarts.hogwarts.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student findStudent(long id) {
        return studentRepository.findById(id).orElseThrow(NotFoundStudentException::new);
    }

    @Override
    public Student editStudent(long id, Student student) {
        Student exist = studentRepository.findById(id).orElseThrow(NotFoundStudentException::new);
        exist.setName(student.getName());
        exist.setAge(student.getAge());
        return studentRepository.save(exist);
    }

    @Override
    public void deleteStudent(long id) {
        studentRepository.findById(id).orElseThrow(NotFoundStudentException::new);
        studentRepository.deleteById(id);
    }

    @Override
    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public List<Student> findByAge(int age) {
        return studentRepository.findByAge(age);
    }

    @Override
    public Long countAllStudents() {
        return studentRepository.countAllStudents();
    }

    @Override
    public Double getAverageAge() {
        return studentRepository.getAverageAge();
    }

    @Override
    public List<Student> getLastFiveStudents() {
        return studentRepository.findTop5ByOrderByIdDesc();
    }
}