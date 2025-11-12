package ru.hogwarts.school.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final AvatarService avatarService;

    @PostMapping
    public Student add(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @PostMapping("/{id}")
    public Student getFacultyById(@PathVariable Long id, @RequestBody Faculty faculty) {
        return studentService.addFacultyToStudentById(id, faculty);
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
    @GetMapping("/students_with_A")
    public List<String> getStudentsNameWithA() {
        return studentService.getStudentsNameWithA();
    }

    @GetMapping("/average_age")
    public Double getAverageAgeStudentsStream() {
        return studentService.getAverageAgeStudentsStream();
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

    @GetMapping("/print-parallel")
    public void printParallel() {
        studentService.printParallel();
    }

    @GetMapping("/print-synchronized")
    public void printSynchronizedParallel() {
        studentService.printSynchronized();
    }

}
