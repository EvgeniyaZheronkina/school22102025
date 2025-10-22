package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    @Autowired
    private FacultyRepository facultyRepository;

    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty editFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Optional<Faculty> getStudentByIdInFaculty(Long id) {
        return facultyRepository.findById(id);
    }
    public void deleteFaculty(Long id){
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getAllStudentFaculty() {
        return facultyRepository.findAll();
    }

    public Collection<Faculty> getFacultiesByColor(String color) {
        return facultyRepository.findFacultiesByColor(color);
    }
}
