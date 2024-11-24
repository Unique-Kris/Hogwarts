package hogwarts.hogwarts.service;

import hogwarts.hogwarts.exception.NotFoundFacultyException;
import hogwarts.hogwarts.model.Faculty;
import hogwarts.hogwarts.repositories.FacultyRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

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
        return facultyRepository.findById(id).orElseThrow(NotFoundFacultyException::new);
    }

    public Faculty editFaculty(long id, Faculty faculty) {
        Faculty exist = facultyRepository.findById(id).orElseThrow(NotFoundFacultyException::new);
        exist.setName(faculty.getName());
        exist.setColor(faculty.getColor());
        return facultyRepository.save(exist);
    }

    public void deleteFaculty(long id) {
        facultyRepository.findById(id).orElseThrow(NotFoundFacultyException::new);
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }
}