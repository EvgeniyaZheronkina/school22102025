package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findStudentByAge(Integer age);

    List<Student> findByAgeBetween(Integer min, Integer max);

    Student getStudentById(Long studentId);
}
