package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {
   private final Map<Long, Student> students = new HashMap<>();
   private Long count = 1L;


    public Student addStudent(Student student) {
        if (student.getId() == null) {
            student.setId(count++);
        }
        students.put(student.getId(), student);
        return student;
    }

    public Student editStudent(Student student) {
        if (student.getId() == null) {
            throw new IllegalArgumentException("Студент не найден");
        }
        return students.put(student.getId(), student);
    }

    public Student getStudentById(Long id) {
        return students.get(id);
    }
    public Student deleteStudent(Long id){
        return students.remove(id);
    }

    public Collection<Student> getAllStudent() {
        return students.values();
    }

    public Collection<Student> getStudentByAge(int age) {
        return students.values().stream()
                .filter(it ->it.getAge() == age)
                .collect(Collectors.toList());
    }
}
