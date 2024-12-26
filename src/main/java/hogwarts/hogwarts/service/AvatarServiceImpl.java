package hogwarts.hogwarts.service;

import hogwarts.hogwarts.model.Avatar;
import hogwarts.hogwarts.repositories.AvatarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class AvatarServiceImpl implements AvatarService {

    private static final Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);

    @Value("${avatars.dir.path}")
    private String avatarsDir;

    private final AvatarRepository avatarRepository;

    public AvatarServiceImpl(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    @Override
    public Avatar uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        logger.info("Was invoked method for uploadAvatar with studentId = {}", studentId);

        Path filePath = Path.of(avatarsDir, studentId + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (var inputStream = file.getInputStream()) {
            Files.copy(inputStream, filePath);
        }

        Avatar avatar = new Avatar();
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());

        logger.debug("Avatar uploaded successfully for studentId = {}", studentId);
        return avatarRepository.save(avatar);
    }

    @Override
    public Avatar getAvatarByStudentId(Long studentId) {
        logger.info("Was invoked method for getAvatarByStudentId with studentId = {}", studentId);

        return avatarRepository.findByStudentId(studentId).orElseThrow(() -> {
            logger.error("There is no avatar for studentId = {}", studentId);
            return new RuntimeException("Avatar not found");
        });
    }

    @Override
    public Page<Avatar> getAllAvatars(Pageable pageable) {
        logger.info("Was invoked method for getAllAvatars");

        return avatarRepository.findAll(pageable);
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}