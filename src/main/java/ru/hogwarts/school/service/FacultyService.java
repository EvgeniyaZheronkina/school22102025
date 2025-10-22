package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();
    private Long count = 1L;

    public Faculty addFaculty(Faculty faculty) {
        if (faculty.getId() == null) {
            faculty.setId(count++);

        }
        return faculties.put(faculty.getId(), faculty);
    }

    public Faculty editFaculty(Faculty faculty) {
        if (faculty.getId() == null) {
            throw new IllegalArgumentException("ID факультета не найден");
        }
        return faculties.put(faculty.getId(), faculty);
    }

    public Faculty getStudentByIdInFaculty(Long id) {
        return faculties.get(id);
    }
    public Faculty deleteFaculty(Long id){
        return faculties.remove(id);
    }

    public Collection<Faculty> getAllStudentFaculty() {
        return faculties.values();
    }

    public Collection<Faculty> getFacultiesByColor(String color) {
        return faculties.values().stream()
                .filter(it -> it.getColor().equals(color))
                .collect(Collectors.toList());
    }
}
