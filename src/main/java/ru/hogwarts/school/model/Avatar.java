package ru.hogwarts.school.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.hogwarts.school.dto.StudentWithFacultyDto;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Avatar {

    @Id
    @GeneratedValue
    private long id;

    private String filePath;
    private long fileSize;
    private String mediaType;

    @Lob
    private byte[] data;

    @OneToOne
    private Student student;
}
