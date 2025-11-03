package ru.hogwarts.school;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.controller.StudentController;


@WebMvcTest(controllers = StudentController.class)
public class StudentServiceMockTest {

    @Autowired
    private MockMvc mockMvc;

}
