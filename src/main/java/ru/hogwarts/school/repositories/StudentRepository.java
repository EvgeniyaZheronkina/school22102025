package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.dto.StudentWithFacultyDto;
import ru.hogwarts.school.model.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findStudentByAge(Integer age);

    List<Student> findByAgeBetween(Integer min, Integer max);

    @Query(value = "SELECT COUNT(*) FROM student", nativeQuery = true)
    Integer getStudentAllCount();

    @Query(value = "SELECT AVG(age) AS average FROM student", nativeQuery = true)
    Double getStudentAverageAge();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> findLastFiveStudent();


}
