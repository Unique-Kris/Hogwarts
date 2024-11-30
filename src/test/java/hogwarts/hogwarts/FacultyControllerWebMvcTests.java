package hogwarts.hogwarts;

import hogwarts.hogwarts.controller.FacultyController;
import hogwarts.hogwarts.model.Faculty;
import hogwarts.hogwarts.repositories.FacultyRepository;
import hogwarts.hogwarts.service.FacultyServiceImpl;
import hogwarts.hogwarts.service.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacultyController.class)
public class FacultyControllerWebMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private FacultyServiceImpl facultyService;

    @MockBean
    private StudentServiceImpl studentService;

    @InjectMocks
    private FacultyController facultyController;

    public static final Faculty FACULTY_1 = new Faculty(1L, "Red", "Faculty 1");
    public static final Faculty FACULTY_2 = new Faculty(2L, "Blue", "Faculty 2");

    @Test
    public void getFacultyByIdTest() throws Exception {
        when(facultyService.findFaculty(1L)).thenReturn(FACULTY_1);

        mockMvc.perform(get("/faculty/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Faculty 1"))
                .andExpect(jsonPath("$.color").value("Red"));
    }

    @Test
    public void getAllFacultyTest() throws Exception {
        when(facultyService.getAllFaculty()).thenReturn(List.of(FACULTY_1, FACULTY_2));

        mockMvc.perform(get("/faculty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Faculty 1"))
                .andExpect(jsonPath("$[0].color").value("Red"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Faculty 2"))
                .andExpect(jsonPath("$[1].color").value("Blue"));
    }

    @Test
    public void getAllFacultyWhenEmptyTest() throws Exception {
        when(facultyService.getAllFaculty()).thenReturn(List.of());

        mockMvc.perform(get("/faculty"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void createFacultyTest() throws Exception {
        Faculty newFaculty = new Faculty(3L, "Green", "Faculty 3");

        when(facultyService.addFaculty(any(Faculty.class))).thenReturn(newFaculty);

        mockMvc.perform(post("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Faculty 3\", \"color\":\"Green\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("Faculty 3"))
                .andExpect(jsonPath("$.color").value("Green"));
    }

    @Test
    public void editFacultyTest() throws Exception {
        Faculty updatedFaculty = new Faculty(1L, "Yellow", "Updated Faculty");

        when(facultyService.editFaculty(eq(1L), any(Faculty.class))).thenReturn(updatedFaculty);

        mockMvc.perform(put("/faculty/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Faculty\", \"color\":\"Yellow\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated Faculty"))
                .andExpect(jsonPath("$.color").value("Yellow"));
    }

    @Test
    public void deleteFacultyTest() throws Exception {
        mockMvc.perform(delete("/faculty/1"))
                .andExpect(status().isNoContent());
    }
}