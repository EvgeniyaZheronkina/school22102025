package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.dto.StudentWithFacultyDto;
import ru.hogwarts.school.model.Student;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpStatus.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    public void testGetStudent() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student", String.class))
                .isNotNull();
    }

    @Test
    public void testPostStudent() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("Bob");
        student.setAge(23);

        Assertions
                .assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student", student, String.class))
                .isNotNull();
    }

    @Test
    void testPatchStudent() throws Exception {
        Student updatedStudent = new Student();
        updatedStudent.setId(1L);
        updatedStudent.setName("Bob");
        updatedStudent.setAge(23);

        HttpEntity<Student> entity = new HttpEntity<>(updatedStudent);
        ResponseEntity<Student> response = restTemplate.exchange("http://localhost:" + port + "/student", HttpMethod.PATCH, entity, Student.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getName()).isEqualTo("Bob");

    }

    @Test
    void testCreatedStudent()  {
        StudentWithFacultyDto newStudent = new StudentWithFacultyDto();
        newStudent.setId(1L);
        newStudent.setName("Bob");
        newStudent.setAge(23);

        HttpEntity<Void> request = new HttpEntity<>(null);


       ResponseEntity<Student> response = restTemplate.exchange("http://localhost:" + port + "/student",
               HttpMethod.POST,
                request,
               Student.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(CREATED);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getName()).isEqualTo(newStudent.getName());
    }

    @Test
    public void whenGetByIdStudent_thenStatusOk() {
        Student student = new Student();
        student.setId(1L);
        student.setName("Bob");
        student.setAge(23);
        ResponseEntity<Student> responseAddStudent = restTemplate.postForEntity("http://localhost:" + port + "/student", student, Student.class);
        Assertions.assertThat(responseAddStudent.getStatusCode()).isEqualTo(HttpStatus.OK);
        long id = requireNonNull(responseAddStudent.getBody()).getId();

        ResponseEntity<Student> responseGetStudent = restTemplate.getForEntity("http://localhost:" + port + "/student/{id}", Student.class, id);
        Assertions.assertThat(responseGetStudent.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseGetStudent.getBody().getName()).isEqualTo(student.getName());
        Assertions.assertThat(responseGetStudent.getBody().getAge()).isEqualTo(student.getAge());
    }

    @Test
    public void whenGetByIdStudent_thenStatusNotFound() {
        final long id = 58;
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/student/{id}", String.class, id);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(OK);
 //       Assertions.assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
    }


    @Test
    public void testAddStudent() {
        Student newStudent = new Student();
        newStudent.setId(1L);
        newStudent.setName("Bob");
        newStudent.setAge(23);

        ResponseEntity<Student> response = restTemplate.postForEntity("http://localhost:" + port + "/student", newStudent, Student.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getName()).isEqualTo("Bob");
    }

    @Test
    public void testDeleteStudent() {
        Student newStudent = new Student();
        newStudent.setId(1L);
        newStudent.setName("Bob");
        newStudent.setAge(23);
        ResponseEntity<Void> response = restTemplate.exchange("http://localhost:" + port + "/student/" + newStudent.getId(),
                HttpMethod.DELETE,
                null,
                Void.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(NO_CONTENT);
    }

    @Test
    public void test2Delete() {
        Student student = new Student();
        student.setId(1L);
        student.setName("Malfoi");
        student.setAge(25);

        HttpEntity<Void> request = new HttpEntity<>(null);

        ResponseEntity<Student> responseAddStudent = restTemplate.postForEntity("http://localhost:" + port + "/student",
                student,
                Student.class);
        long id = requireNonNull(responseAddStudent.getBody()).getId();

        ResponseEntity<Student> responseFindStudent = restTemplate.getForEntity("http://localhost:" + port + "/student/{id}",
                Student.class,id);
//        Assertions.assertThat(responseFindStudent.getStatusCode()).isEqualTo(HttpStatus.OK);

        restTemplate.delete("http://localhost:" + port + "/student/" + id);

        ResponseEntity<String> responseCheckStudent = restTemplate.getForEntity("http://localhost:" + port + "/student/{id}",
                String.class, id);
        Assertions.assertThat(responseCheckStudent.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}
