package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findStudentByAge(Integer age);

    List<Student> findByAgeBetween(Integer min, Integer max);
}
