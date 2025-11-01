package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findStudentByAge(Integer age);

    List<Student> findByAgeBetween(Integer min, Integer max);

    @Query(value = "SELECT COUNT(DISTINCT(name)) FROM hogwarts", nativeQuery = true)
    List<Student> getStudentAllCount();

    @Query(value = "SELECT AVG(age) AS average FROM hogwarts", nativeQuery = true)
    List<Student> getStudentAverageAge();

    @Query(value = "SELECT AVG(age) AS average FROM hogwarts", nativeQuery = true)
    List<Student> getFiveStudent();


}
