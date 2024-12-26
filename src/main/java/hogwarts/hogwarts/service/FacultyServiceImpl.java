package hogwarts.hogwarts.service;

import hogwarts.hogwarts.exception.NotFoundFacultyException;
import hogwarts.hogwarts.model.Faculty;
import hogwarts.hogwarts.repositories.FacultyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class FacultyServiceImpl implements FacultyService {

    private static final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        logger.info("Was invoked method for addFaculty with faculty = {}", faculty);

        Faculty savedFaculty = facultyRepository.save(faculty);
        logger.debug("Faculty added successfully: {}", savedFaculty);
        return savedFaculty;
    }

    public Faculty findFaculty(long id) {
        logger.info("Was invoked method for findFaculty with id = {}", id);

        return facultyRepository.findById(id).orElseThrow(() -> {
            logger.error("Faculty not found with id = {}", id);
            return new NotFoundFacultyException();
        });
    }

    public Faculty editFaculty(long id, Faculty faculty) {
        logger.info("Was invoked method for editFaculty with id = {}", id);

        Faculty exist = facultyRepository.findById(id).orElseThrow(() -> {
            logger.error("Faculty not found with id = {}", id);
            return new NotFoundFacultyException();
        });

        exist.setName(faculty.getName());
        exist.setColor(faculty.getColor());
        Faculty updatedFaculty = facultyRepository.save(exist);
        logger.debug("Faculty updated successfully: {}", updatedFaculty);

        return updatedFaculty;
    }

    public void deleteFaculty(long id) {
        logger.info("Was invoked method for deleteFaculty with id = {}", id);

        facultyRepository.findById(id).orElseThrow(() -> {
            logger.error("Faculty not found with id = {}", id);
            return new NotFoundFacultyException();
        });

        facultyRepository.deleteById(id);
        logger.debug("Faculty deleted successfully with id = {}", id);
    }

    public Collection<Faculty> getAllFaculty() {
        logger.info("Was invoked method for getAllFaculty");

        Collection<Faculty> allFaculties = facultyRepository.findAll();
        logger.debug("Retrieved all faculties: {}", allFaculties);

        return allFaculties;
    }
}