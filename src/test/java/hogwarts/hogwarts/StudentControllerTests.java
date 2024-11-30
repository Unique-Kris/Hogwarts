package hogwarts.hogwarts;

import hogwarts.hogwarts.controller.StudentController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    public void testDefaultMessage() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/", String.class))
                .contains("WebApp is running");
    }

    @Test
    public void testGetStudent() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student", String.class))
                .isNotEmpty();
    }
}