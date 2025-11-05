package ru.hogwarts.school.dto;

import lombok.Data;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class FacultyWithStudentsDto {
    private long id;
    private String name;
    private String color;
    private List<StudentWithFacultyDto> students;

    public static FacultyWithStudentsDto of(Faculty faculty) {
        FacultyWithStudentsDto dto = new FacultyWithStudentsDto();
        dto.setId(faculty.getId());
        dto.setName(faculty.getName());
        dto.setColor(faculty.getColor());
        dto.setStudents(
                faculty.getStudents().stream()
                        .map(StudentWithFacultyDto::of)
                        .collect(Collectors.toList())
        );
        return dto;
    }
}
