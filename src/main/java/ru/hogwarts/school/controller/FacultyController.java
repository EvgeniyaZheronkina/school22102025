package ru.hogwarts.school.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.dto.FacultyWithStudentsDto;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;


    @PostMapping
    public Faculty add(@RequestBody Faculty faculty) {
       return facultyService.addFaculty(faculty);
    }

    @GetMapping("/{id}")
    public FacultyWithStudentsDto getFacultyById(@PathVariable Long id) {
        return facultyService.getStudentByIdInFaculty(id);
    }

    @GetMapping
    public List<Faculty> findFaculty(@RequestParam(required = false) String name,
                                     @RequestParam(required = false) String color) {
        if (name != null && !name.isBlank()) {
            return facultyService.findFacultyByName(name);
        }
        if (color != null && !color.isBlank()) {
            return facultyService.getFacultiesByColor(color);
        }
        return facultyService.getAllStudentFaculty();
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
