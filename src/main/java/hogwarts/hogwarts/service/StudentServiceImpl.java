package hogwarts.hogwarts.service;

import hogwarts.hogwarts.exception.NotFoundStudentException;
import hogwarts.hogwarts.model.Student;
import hogwarts.hogwarts.repositories.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student addStudent(Student student) {
        logger.info("Was invoked method for addStudent with student = {}", student);

        Student savedStudent = studentRepository.save(student);
        logger.debug("Student added successfully: {}", savedStudent);
        return savedStudent;
    }

    @Override
    public Student findStudent(long id) {
        logger.info("Was invoked method for findStudent with id = {}", id);

        return studentRepository.findById(id).orElseThrow(() -> {
            logger.error("Student not found with id = {}", id);
            return new NotFoundStudentException();
        });
    }

    @Override
    public Student editStudent(long id, Student student) {
        logger.info("Was invoked method for editStudent with id = {}", id);

        Student exist = studentRepository.findById(id).orElseThrow(() -> {
            logger.error("Student not found with id = {}", id);
            return new NotFoundStudentException();
        });

        exist.setName(student.getName());
        exist.setAge(student.getAge());
        Student updatedStudent = studentRepository.save(exist);
        logger.debug("Student updated successfully: {}", updatedStudent);

        return updatedStudent;
    }

    @Override
    public void deleteStudent(long id) {
        logger.info("Was invoked method for deleteStudent with id = {}", id);

        studentRepository.findById(id).orElseThrow(() -> {
            logger.error("Student not found with id = {}", id);
            return new NotFoundStudentException();
        });

        studentRepository.deleteById(id);
        logger.debug("Student deleted successfully with id = {}", id);
    }

    @Override
    public Collection<Student> getAllStudents() {
        logger.info("Was invoked method for getAllStudents");

        Collection<Student> allStudents = studentRepository.findAll();
        logger.debug("Retrieved all students: {}", allStudents);

        return allStudents;
    }

    @Override
    public List<Student> findByAge(int age) {
        logger.info("Was invoked method for findByAge with age = {}", age);

        List<Student> studentsByAge = studentRepository.findByAge(age);
        logger.debug("Found students by age {}: {}", age, studentsByAge);

        return studentsByAge;
    }

    @Override
    public Long countAllStudents() {
        logger.info("Was invoked method for countAllStudents");

        Long count = studentRepository.countAllStudents();
        logger.debug("Total number of students: {}", count);

        return count;
    }

    @Override
    public Double getAverageAge() {
        logger.info("Was invoked method for getAverageAge");

        Double averageAge = studentRepository.getAverageAge();
        logger.debug("Average age of students: {}", averageAge);

        return averageAge;
    }

    @Override
    public List<Student> getLastFiveStudents() {
        logger.info("Was invoked method for getLastFiveStudents");

        List<Student> lastFiveStudents = studentRepository.findTop5ByOrderByIdDesc();
        logger.debug("Last five students: {}", lastFiveStudents);

        return lastFiveStudents;
    }
}