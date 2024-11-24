package hogwarts.hogwarts.controller;

import hogwarts.hogwarts.exception.NotFoundFacultyException;
import hogwarts.hogwarts.model.Faculty;
import hogwarts.hogwarts.model.Student;
import hogwarts.hogwarts.service.FacultyService;
import hogwarts.hogwarts.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    private FacultyService facultyService;

    public StudentController(StudentService studentService, FacultyService facultyService) {
        this.studentService = studentService;
        this.facultyService = facultyService;
    }

    @GetMapping("/{id}/faculty")
    public Faculty getFacultyOfStudent(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student != null) {
            return student.getFaculty();
        } else {
            throw new NotFoundFacultyException();
        }
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentService.findStudent(id);
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {  // Исправил на createStudent
        return studentService.addStudent(student);
    }

    @PutMapping("/{id}")
    public Student editStudent(@PathVariable Long id, @RequestBody Student student) {  // Исправил на editStudent
        return studentService.editStudent(id, student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }
}