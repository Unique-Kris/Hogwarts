package hogwarts.hogwarts.service;

import hogwarts.hogwarts.model.Avatar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AvatarService {

    Avatar uploadAvatar(Long studentId, MultipartFile file) throws IOException;

    Avatar getAvatarByStudentId(Long studentId);

    Page<Avatar> getAllAvatars(Pageable pageable);
}