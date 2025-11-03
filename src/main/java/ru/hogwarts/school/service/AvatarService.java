package ru.hogwarts.school.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.dto.StudentWithFacultyDto;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@RequiredArgsConstructor
@Transactional
public class AvatarService {
    private final AvatarRepository avatarRepository;
    private final StudentService studentService;

    @Value("${path.to.avatars.folder}")
    private String avatarDir;


    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        Student student = studentService.findStudent(studentId);

        Path filePath = Path.of(avatarDir, studentId + "." + getExtensions(Objects.requireNonNull(file.getOriginalFilename())));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }

        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(generateDataForDB(filePath));

        avatarRepository.save(avatar);
    }

    public void deleteAvatar(Long id) throws IOException {
        Optional<Avatar> avatarOptional = avatarRepository.findByStudentId(id);
        if (avatarOptional.isPresent()) {
            Avatar avatar = avatarOptional.get();
            Path avatarPath = Paths.get(avatar.getFilePath());
            Files.deleteIfExists(avatarPath);  // удаляем файл аватарки из файловой системы
            avatarRepository.delete(avatar);  // удаляем запись аватарки из базы данных
        }
    }

    public Avatar findAvatar(Long studentId) {
        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());
    }

    private byte[] generateDataForDB(Path filePath) throws IOException{
        try (
                InputStream is = Files.newInputStream(filePath);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                ByteArrayOutputStream baos = new ByteArrayOutputStream()
        ) {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics20 = preview.createGraphics();
            graphics20.drawImage(image, 0, 0, 100, height, null);
            graphics20.dispose();

            ImageIO.write(preview, getExtensions(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}
