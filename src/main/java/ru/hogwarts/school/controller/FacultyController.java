package ru.hogwarts.school.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;


    @PostMapping
    public ResponseEntity<Faculty> add(@RequestBody Faculty faculty) {
        Faculty createdFaculty = facultyService.addFaculty(faculty);
        return ResponseEntity.ok(createdFaculty);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Faculty> get(@PathVariable Long id) {
//        Faculty Faculty = facultyService.getStudentByIdInFaculty(id);
//        if (Faculty == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(Faculty);
//    }

    @GetMapping
    public Collection<Faculty> getAll() {
        return facultyService.getAllStudentFaculty();
    }

    @GetMapping("{color}")
    public Collection<Faculty> getFaculty(@PathVariable String color) {
        return facultyService.getFacultiesByColor(color);
    }

    @PatchMapping
    public ResponseEntity<Faculty> edit(@RequestBody Faculty faculty) {
        Faculty editFc = facultyService.editFaculty(faculty);
        return ResponseEntity.ok(editFc);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
    }


}
