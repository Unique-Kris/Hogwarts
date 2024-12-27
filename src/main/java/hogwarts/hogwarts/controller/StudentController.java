package hogwarts.hogwarts.controller;

import hogwarts.hogwarts.exception.NotFoundFacultyException;
import hogwarts.hogwarts.model.Faculty;
import hogwarts.hogwarts.model.Student;
import hogwarts.hogwarts.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
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

    @GetMapping("/{age}/age")
    public ResponseEntity<List<Student>> findByAge(@PathVariable int age) {
        return ResponseEntity.ok(studentService.findByAge(age));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getStudentCount() {
        Long count = studentService.countAllStudents();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/average-age")
    public ResponseEntity<Double> getAverageAge() {
        Double averageAge = studentService.getAverageAge();
        return ResponseEntity.ok(averageAge);
    }

    @GetMapping("/last-five")
    public ResponseEntity<List<Student>> getLastFiveStudents() {
        List<Student> lastFive = studentService.getLastFiveStudents();
        return ResponseEntity.ok(lastFive);
    }

    @GetMapping("/names-starting-with-a")
    public ResponseEntity<Collection<String>> getNamesStartingWithA() {
        Collection<Student> students = studentService.getAllStudents();
        Collection<String> filteredNames = students.stream()
                .map(Student::getName)
                .filter(name -> name.toUpperCase().startsWith("A"))
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.toList());

        return ResponseEntity.ok(filteredNames);
    }

    @GetMapping("/average-age-stream")
    public ResponseEntity<Double> getAverageAgeStream() {
        Collection<Student> students = studentService.getAllStudents();
        double averageAge = students.stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0.0);
        return ResponseEntity.ok(averageAge);
    }

    @GetMapping("/sum")
    public ResponseEntity<Integer> getSum() {
        int sum = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);

        return ResponseEntity.ok(sum);
    }

    @GetMapping("/print-parallel")
    public void printStudentsInParallel() {
        Collection<Student> students = studentService.getAllStudents();
        List<String> studentNames = students.stream()
                .map(Student::getName)
                .toList();

        if (studentNames.size() < 6) {
            System.out.println("Недостаточно студентов для выполнения операции");
            return;
        }

        System.out.println(studentNames.get(0));
        System.out.println(studentNames.get(1));

        new Thread(() -> {
            System.out.println(studentNames.get(2));
            System.out.println(studentNames.get(3));
        }).start();

        new Thread(() -> {
            System.out.println(studentNames.get(4));
            System.out.println(studentNames.get(5));
        }).start();
    }

    @GetMapping("/print-synchronized")
    public void printStudentsSynchronized() {
        Collection<Student> students = studentService.getAllStudents();
        List<String> studentNames = students.stream()
                .map(Student::getName)
                .toList();

        if (studentNames.size() < 6) {
            System.out.println("Недостаточно студентов для выполнения операции");
            return;
        }

        studentService.synchronizedPrint(studentNames.get(0));
        studentService.synchronizedPrint(studentNames.get(1));

        new Thread(() -> {
            studentService.synchronizedPrint(studentNames.get(2));
            studentService.synchronizedPrint(studentNames.get(3));
        }).start();

        new Thread(() -> {
            studentService.synchronizedPrint(studentNames.get(4));
            studentService.synchronizedPrint(studentNames.get(5));
        }).start();
    }
}