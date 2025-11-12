package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.dto.FacultyWithStudentsDto;
import ru.hogwarts.school.dto.StudentWithFacultyDto;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@Service
public class FacultyService {

    @Autowired
    private FacultyRepository facultyRepository;

    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public Faculty addFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty.");
        Faculty saveFaculty = facultyRepository.save(faculty);
        logger.debug("A faculty was created in the database upon request - {}.", saveFaculty);
        return saveFaculty;
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.info("Was invoked method for change faculty.");
        return facultyRepository.save(faculty);
    }

    public FacultyWithStudentsDto getStudentByIdInFaculty(Long id) {
        logger.info("Was invoked method for find student by Id in faculty.");
        return facultyRepository.findById(id)
                .map(it ->
                        FacultyWithStudentsDto.of(it))
                .orElse(null);
    }

    public void deleteFaculty(Long id) {
        logger.info("Was invoked method for delete faculty by id.");
        validateId(id);
        Faculty findFaculty = facultyRepository.findById(id)
                .map(it -> {
                    Faculty faculty = new Faculty();
                    faculty.setId(it.getId());
                    faculty.setName(it.getName());
                    faculty.setColor(it.getColor());
                    return faculty;
                }).orElse(null);
        facultyRepository.deleteById(id);
        logger.debug("{} was delete.", findFaculty);
    }

    public List<Faculty> getAllFaculty() {
        logger.info("Was invoked method for get all faculties.");
        return facultyRepository.findAll();
    }

    public List<Faculty> getFacultiesByColor(String color) {
        logger.info("Was invoked method for find faculty by color.");
        return facultyRepository.findFacultiesByColorIgnoreCase(color);
    }

    public List<Faculty> findFacultyByName(String name) {
        logger.info("Was invoked method for find faculty by name.");
        return facultyRepository.findFacultyByNameIgnoreCase(name);
    }

    public String findLongNameFaculty() {
        logger.info("Was invoked method for find faculty by long name.");
        List<Faculty> faculties = facultyRepository.findAll();
        String longName = faculties.stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .get();
        return longName;
    }

    public Long getNumber() {
        logger.info("Was invoked method for get int number.");
        long startTime = System.currentTimeMillis();
        long sum = LongStream.iterate(1L, a -> a + 1)
                .limit(1_000_000)
                .parallel()
                .reduce(0L, Long::sum);
        long finishTime = System.currentTimeMillis() - startTime;
        logger.info("время работы метода - {}", finishTime);
        return sum;
    }

    private void validateId(long id) {
        if (facultyRepository.findById(id).isEmpty()) {
            logger.error("There is not faculty with id = {}", id);
            throw new RuntimeException("Факультета с id = " + id + " не существует");
        }
    }


}
