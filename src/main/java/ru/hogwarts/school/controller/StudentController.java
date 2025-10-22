package ru.hogwarts.school.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;


    @PostMapping
    public Student add(@RequestBody Student student) {
        Student createdStudent = studentService.addStudent(student);
        return createdStudent;
    }

    @GetMapping
    public Collection<Student> getAll() {
        return studentService.getAllStudent();
    }

    @GetMapping("{age}")
    public Collection<Student> getFaculties(@PathVariable int age) {
        return studentService.getStudentByAge(age);
    }

    @PatchMapping
    public ResponseEntity<Student> edit(@RequestBody Student student) {
        Student editSt = studentService.editStudent(student);
        return ResponseEntity.ok(editSt);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        studentService.deleteStudent(id);
    }


}
