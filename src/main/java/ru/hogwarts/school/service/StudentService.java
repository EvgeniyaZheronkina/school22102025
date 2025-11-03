package ru.hogwarts.school.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.dto.StudentWithFacultyDto;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.awt.*;
import java.io.*;
import java.util.List;


@Service
public class StudentService {

    private final StudentRepository studentRepository;


    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;

    }

    public Student addStudent(Student studentDto) {
        studentDto.setId(null);
       return studentRepository.save(studentDto);
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


}
