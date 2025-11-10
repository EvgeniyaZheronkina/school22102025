package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.dto.FacultyWithStudentsDto;
import ru.hogwarts.school.dto.StudentWithFacultyDto;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

    public FacultyWithStudentsDto getStudentByIdInFaculty(Long id) {
        return facultyRepository.findById(id)
                .map(it ->
                    FacultyWithStudentsDto.of(it))
                .orElse(null);
    }

    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }

    public List<Faculty> getAllStudentFaculty() {
        return facultyRepository.findAll();
    }

    public List<Faculty> getFacultiesByColor(String color) {
        return facultyRepository.findFacultiesByColorIgnoreCase(color);
    }

    public List<Faculty> findFacultyByName(String name) {
        return facultyRepository.findFacultyByNameIgnoreCase(name);
    }


}
