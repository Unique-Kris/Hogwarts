package hogwarts.hogwarts;

import hogwarts.hogwarts.controller.FacultyController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    public void testDefaultMessage() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/", String.class))
                .contains("WebApp is running");
    }

    @Test
    public void testGetFaculty() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty", String.class))
                .isNotEmpty();
    }
}