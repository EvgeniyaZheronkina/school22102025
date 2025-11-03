package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.dto.FacultyWithStudentsDto;
import ru.hogwarts.school.repositories.FacultyRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private FacultyRepository facultyRepository;

    private FacultyWithStudentsDto testFacultyWithStudentsDto;

    @BeforeEach
    void createEntityTest() {
        testFacultyWithStudentsDto = new FacultyWithStudentsDto();
        testFacultyWithStudentsDto.setName("Richard");
        testFacultyWithStudentsDto.setColor("blue");

    }

    @AfterEach
    void deleteEntityTest() {
        if (facultyRepository.existsById(testFacultyWithStudentsDto.getId())) {
            facultyRepository.deleteById(testFacultyWithStudentsDto.getId());
        }
    }

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }

}
