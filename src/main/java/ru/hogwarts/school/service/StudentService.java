package ru.hogwarts.school.service;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister;
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

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    public Student addStudent(Student student) {
        logger.info("Was invoked method for create student");
        student.setId(null);
        Student saveStudent = studentRepository.save(student);
        logger.debug("A student was created in the database upon request - {}.", saveStudent);
        return saveStudent;
    }

    public Student addFacultyToStudentById(Long id, Faculty faculty) {
        logger.info("Was invoked method for add faculty to student by Id");
        return studentRepository.findById(id)
                .map(it -> {
                    if (it.getFaculty() != null) {
                        logger.warn("Данный студент уже приписан к факультету: {}", faculty.getName());
                        return null;
                    }
                    Student dto = new Student();
                    dto.setId(it.getId());
                    dto.setName(it.getName());
                    dto.setAge(it.getAge());
                    dto.setFaculty(faculty);
                    logger.debug("Студенту присвоен факультет {}", dto);
                    return studentRepository.save(dto);
                }).orElseThrow();
    }

    public List<Student> getAllStudent() {
        logger.info("Was invoked method for get all students");
        return studentRepository.findAll();
    }

    public Student editStudent(Student student) {
        logger.info("Was invoked method for change student.");
        return studentRepository.save(student);
    }

    //Не получается выкинуть ошибку со статусом Not_found
    public Student findStudent(long id) {
        logger.info("Was invoked method for find student by Id.");
        Student findStudent = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Студент не найден"));;
        logger.debug("A student - {}, was found by id - {}.", findStudent.getName(), id);
        return findStudent;
    }

    public void deleteStudent(Long id) throws IOException {
        logger.info("Was invoked method for delete student by id.");
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Студент не найден"));
        logger.debug("{} was delete.", student.getName());
        studentRepository.deleteById(id);
    }

    public List<Student> getStudentByAge(int age) {
        logger.info("Was invoked method for get students by age.");
        return studentRepository.findStudentByAge(age);
    }

    public List<Student> findByAgeBetween(int min, int max) {
        logger.info("Was invoked method for get students by age between period.");
        return studentRepository.findByAgeBetween(min, max);
    }

    public Integer getStudentAllCount() {
        logger.info("Was invoked method for get count all students.");
        return studentRepository.getStudentAllCount();
    }

    public Double getStudentAverageAge() {
        logger.info("Was invoked method for get average age student.");
        return studentRepository.getStudentAverageAge();
    }

    public List<Student> findLastFiveStudent() {
        logger.info("Was invoked method for get 5 last students.");
        return studentRepository.findLastFiveStudent();
    }

    public List<String> getStudentsNameWithA() {
        logger.info("Was invoked method for get all students who have name with A.");
        List<Student> students = studentRepository.findAll();
        List<String> sortedStudents = students.stream()
                .filter(e -> e.getName().toUpperCase().startsWith("A"))
                .map(Student::getName)
                .sorted()
                .toList();
        logger.debug("The list of students whose names begin with A in the database is {}", sortedStudents);
        return sortedStudents;
    }

    public Double getAverageAgeStudentsStream() {
        logger.info("Was invoked method for get average age aa students.");
        List<Student> students = studentRepository.findAll();
        double averageAge = students.stream()
                .mapToDouble(Student::getAge)
                .average()
                .getAsDouble();
        logger.debug("The average age of a student in the database is {}", averageAge);
        return averageAge;
    }


    public void printParallel() {
        logger.info("Was invoked method for get all students names in parallel mode.");
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

    public void printSynchronized() {
        logger.info("Was invoked method for get all students names in parallel mode with synchronized.");
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

    private void validateId(long id)  {
        if (studentRepository.findById(id).isEmpty()) {
            logger.error("There is not student with id = {}.", id);
            throw new RuntimeException("Студент с данным Id не найден");
        }
    }

}
