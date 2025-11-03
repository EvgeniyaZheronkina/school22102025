package ru.hogwarts.school.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.dto.StudentWithFacultyDto;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final AvatarService avatarService;

    @PostMapping
    public Student add(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @GetMapping("/{id}")
    public Student getStudentInfo(@PathVariable Long id) {
        return studentService.findStudent(id);
    }

    @GetMapping
    public List<Student> getAll(@RequestParam(required = false) Integer age,
                                                @RequestParam(required = false) Integer min,
                                                @RequestParam(required = false) Integer max) {
        if (age != null) {
            return studentService.getStudentByAge(age);
        }
        if (min != null && max != null) {
            return studentService.findByAgeBetween(min, max);
        }
        return studentService.getAllStudent();
    }

    @PatchMapping
    public Student edit(@RequestBody Student student) {
        return studentService.editStudent(student);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) throws IOException {
        if (avatarService.findAvatar(id) != null) {
            avatarService.deleteAvatar(id);
        }
        studentService.deleteStudent(id);
    }

    @GetMapping("/count-all-student")
    public int getStudentAllCount() {
        return studentService.getStudentAllCount();
    }

    @GetMapping("/average-students")
    public double getStudentAverageAge() {
        return studentService.getStudentAverageAge();
    }

    @GetMapping("/last-five-student")
    public List<Student> findLastFiveStudent() {
        return studentService.findLastFiveStudent();
    }
}
