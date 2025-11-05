package ru.hogwarts.school.dto;

import lombok.Data;
import ru.hogwarts.school.model.Student;

@Data
public class StudentWithFacultyDto {

    private Long id;
    private String name;
    private int age;


    public static StudentWithFacultyDto of(Student student) {
        StudentWithFacultyDto studentWithFacultyDto = new StudentWithFacultyDto();
        studentWithFacultyDto.setId(student.getId());
        studentWithFacultyDto.setName(student.getName());
        studentWithFacultyDto.setAge(student.getAge());
        return studentWithFacultyDto;
    }

}
