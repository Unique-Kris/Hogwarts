package hogwarts.hogwarts.controller;

import hogwarts.hogwarts.model.Avatar;
import hogwarts.hogwarts.service.AvatarService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/avatar")
public class AvatarController {

    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    // Загрузка аватара для студента
    @PostMapping("/{studentId}")
    public ResponseEntity<Avatar> uploadAvatar(@PathVariable Long studentId, @RequestParam("file") MultipartFile file) {
        try {
            Avatar avatar = avatarService.uploadAvatar(studentId, file);
            return ResponseEntity.ok(avatar);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Avatar> getAvatar(@PathVariable Long studentId) {
        try {
            Avatar avatar = avatarService.getAvatarByStudentId(studentId);
            return ResponseEntity.ok(avatar);
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Avatar>> getAllAvatars(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Avatar> avatars = avatarService.getAllAvatars(pageRequest);
        return ResponseEntity.ok(avatars);
    }
}