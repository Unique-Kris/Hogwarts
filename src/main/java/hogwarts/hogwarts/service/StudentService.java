package hogwarts.hogwarts.service;

import hogwarts.hogwarts.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {

    Student addStudent(Student student);

    Student findStudent(long id);

    Student editStudent(long id, Student student);

    void deleteStudent(long id);

    Collection<Student> getAllStudents();

    List<Student> findByAge(int age);

    Long countAllStudents();

    Double getAverageAge();

    List<Student> getLastFiveStudents();

    void synchronizedPrint(String name);
}