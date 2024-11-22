package hogwarts.hogwarts.service;

import hogwarts.hogwarts.exception.FacultyAlreadyExistsException;
import hogwarts.hogwarts.model.Faculty;
import hogwarts.hogwarts.repositories.FacultyRepository;
import org.springframework.stereotype.Service;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        return facultyRepository.findById(id).get();
    }

    public Faculty editFaculty(long id, Faculty faculty) {
        if (!facultyRepository.existsById(id)) {
            throw new FacultyAlreadyExistsException();
        }
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }
}
