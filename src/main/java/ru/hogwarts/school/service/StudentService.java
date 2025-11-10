package ru.hogwarts.school.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.io.*;
import java.util.List;


@Service
public class StudentService {

    private final StudentRepository studentRepository;


    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;

    }

    public Student addStudent(Student student) {
        student.setId(null);
        return studentRepository.save(student);
    }

    public Student addFacultyByIdInStudent(Long id, Faculty faculty) {
        return studentRepository.findById(id)
                .map(it -> {
                    Student dto = new Student();
                    dto.setId(it.getId());
                    dto.setName(it.getName());
                    dto.setAge(it.getAge());
                    dto.setFaculty(faculty);
                    return studentRepository.save(dto);
                }).orElse(null);

    }

    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    public Student editStudent(Student student) {
        return studentRepository.save(student);
    }

    //Не получается выкинуть ошибку со статусом Not_found
    public Student findStudent(long id) {
        return studentRepository.findById(id).orElseThrow();
    }

    public void deleteStudent(Long id) throws IOException {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Студент не найден"));
        studentRepository.deleteById(id);
    }

    public List<Student> getStudentByAge(int age) {
        return studentRepository.findStudentByAge(age);
    }

    public List<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public Integer getStudentAllCount() {
        return studentRepository.getStudentAllCount();
    }

    public Double getStudentAverageAge() {
        return studentRepository.getStudentAverageAge();
    }

    public List<Student> findLastFiveStudent() {
        return studentRepository.findLastFiveStudent();
    }

    public void printParallel() {
        List<Student> students = studentRepository.findAll();

        System.out.println("Первый студент - " + students.get(0).getName());
        System.out.println("Второй студент -" + students.get(1).getName());

        new Thread(() -> {
            try {
                System.out.println("Третий студент - " + students.get(2).getName());
                Thread.sleep(300);
                System.out.println("Четвертый студент -" + students.get(3).getName());
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(() -> {
            try {
                System.out.println("Пятый студент - " + students.get(4).getName());
                Thread.sleep(300);
                System.out.println("Шестой студент -" + students.get(5).getName());
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void printSynchronizedParallel() {
        List<Student> students = studentRepository.findAll();

        printNameStudent(students, 0);
        printNameStudent(students, 1);

        new Thread(() -> {
            printNameStudent(students, 2);
            printNameStudent(students, 3);

        }).start();

        new Thread(() -> {
            printNameStudent(students, 4);
            printNameStudent(students, 5);
        }).start();
    }

    private synchronized void printNameStudent(List<Student> students, int number) {
        System.out.println(number + "студент - " + students.get(number).getName());
    }

}
