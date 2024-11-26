package hogwarts.hogwarts;

import hogwarts.hogwarts.controller.StudentController;
import hogwarts.hogwarts.model.Avatar;
import hogwarts.hogwarts.model.Student;
import hogwarts.hogwarts.repositories.AvatarRepository;
import hogwarts.hogwarts.repositories.FacultyRepository;
import hogwarts.hogwarts.repositories.StudentRepository;
import hogwarts.hogwarts.service.FacultyServiceImpl;
import hogwarts.hogwarts.service.StudentServiceImpl;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
public class StudentControllerWebMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private AvatarRepository avatarRepository;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private StudentServiceImpl studentService;

    @SpyBean
    private FacultyServiceImpl facultyService;

    @InjectMocks
    private StudentController studentController;

    public static final Student STUDENT_1 = new Student(1, "name", 18);
    public static final Student STUDENT_2 = new Student(2, "name2", 20);

    @Test
    public void getStudentInfoTest() throws Exception {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(STUDENT_1));

        mockMvc.perform(get("/student/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.age").value(18));
    }

    @Test
    public void getAllStudentsTest() throws Exception {
        when(studentRepository.findAll()).thenReturn(List.of(STUDENT_1, STUDENT_2));

        mockMvc.perform(get("/student"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("name"))
                .andExpect(jsonPath("$[0].age").value(18))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("name2"))
                .andExpect(jsonPath("$[1].age").value(20));
    }

    @Test
    public void findByAgeTest() throws Exception {
        when(studentService.findByAge(18)).thenReturn(List.of(STUDENT_1));

        mockMvc.perform(get("/student/{age}/age", 18))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(STUDENT_1.getId()))
                .andExpect(jsonPath("$[0].name").value(STUDENT_1.getName()))
                .andExpect(jsonPath("$[0].age").value(STUDENT_1.getAge()));
    }

    @Test
    public void createStudentTest() throws Exception {
        JSONObject studentObject = new JSONObject();
        studentObject.put("name", "name");
        studentObject.put("age", 18);

        when(studentRepository.save(any(Student.class))).thenReturn(STUDENT_1);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(STUDENT_1));

        mockMvc.perform(post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.age").value(18));
    }

    @Test
    public void editStudentTest() throws Exception {
        Student updatedStudent = new Student(1, "updated name", 19);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(STUDENT_1));
        when(studentRepository.save(any(Student.class))).thenReturn(updatedStudent);

        mockMvc.perform(put("/student/1")
                        .content("{\"name\":\"updated name\",\"age\":19}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("updated name"))
                .andExpect(jsonPath("$.age").value(19));
    }

    @Test
    public void deleteStudentTest() throws Exception {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(STUDENT_1));

        mockMvc.perform(delete("/student/1"))
                .andExpect(status().isOk());

        verify(studentRepository).deleteById(1L);
    }

    @Test
    public void uploadAvatarTest() throws Exception {
        Avatar avatar = new Avatar();
        avatar.setStudent(STUDENT_1);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(STUDENT_1));
        when(avatarRepository.findByStudentId(1L)).thenReturn(Optional.of(avatar));

        MockMultipartFile file = new MockMultipartFile("avatar", "fake-file.png", MediaType.IMAGE_PNG_VALUE, "fake-file-content".getBytes());

        mockMvc.perform(multipart("/student/1/avatar")
                        .file(file))
                .andExpect(status().isOk());

        verify(avatarRepository).save(any(Avatar.class));
    }

    @Test
    public void downloadAvatarPreviewTest() throws Exception {
        byte[] avatarData = new byte[]{1, 2, 3, 4};
        Avatar avatar = new Avatar();
        avatar.setFilePath("/path/to/avatar.png");
        avatar.setMediaType("image/png");
        avatar.setFileSize(avatarData.length);
        avatar.setData(avatarData);

        when(avatarRepository.findByStudentId(1L)).thenReturn(Optional.of(avatar));

        mockMvc.perform(get("/student/1/avatar/preview"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, "image/png"))
                .andExpect(content().bytes(avatarData));
    }

    @Test
    public void downloadAvatarFileTest() throws Exception {
        File tempFile = Files.createTempFile("avatar", ".png").toFile();
        tempFile.deleteOnExit();

        Avatar avatar = new Avatar();
        avatar.setStudent(STUDENT_1);
        avatar.setFilePath(tempFile.getAbsolutePath());
        avatar.setMediaType("image/png");
        avatar.setFileSize(tempFile.length());

        when(avatarRepository.findByStudentId(1L)).thenReturn(Optional.of(avatar));

        mockMvc.perform(get("/student/1/avatar"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType("image/png")));
    }
}