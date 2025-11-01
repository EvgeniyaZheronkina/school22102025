package ru.hogwarts.school.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

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
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAll(@RequestParam(required = false) Integer age,
                                                @RequestParam(required = false) Integer min,
                                                @RequestParam(required = false) Integer max) {
        if (age != null) {
            return ResponseEntity.ok(studentService.getStudentByAge(age));
                    //studentService.getStudentByAge(age);
        }
        if (min != null && max != null) {
            return ResponseEntity.ok(studentService.findByAgeBetween(min, max));
                    //studentService.findByAgeBetween(min, max);
        }
        return ResponseEntity.ok(studentService.getAllStudent());
                //studentService.getAllStudent();
    }

    @PatchMapping
    public ResponseEntity<Student> edit(@RequestBody Student student) {
        Student foundStudent = studentService.editStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable ("id") long id) throws IOException {
        avatarService.deleteAvatar(id);
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }


}
