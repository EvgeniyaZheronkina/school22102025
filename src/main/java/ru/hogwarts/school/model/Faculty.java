package ru.hogwarts.school.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.hogwarts.school.dto.StudentWithFacultyDto;

import java.util.Collection;
import java.util.List;
import java.util.Set;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Faculty{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String color;

    @JsonIgnore
    @OneToMany(mappedBy = "faculty")
    private List<Student> students;


}
