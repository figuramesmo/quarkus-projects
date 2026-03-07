package ada.caixa.service;

import ada.caixa.dto.CourseRequestDTO;
import ada.caixa.dto.CourseResponseDTO;
import ada.caixa.entity.Course;
import ada.caixa.repository.CourseRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@ApplicationScoped
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Transactional
    public CourseResponseDTO createCourse(CourseRequestDTO courseRequestDTO) {
        String name = courseRequestDTO.name();
        String description = courseRequestDTO.description();

        Course course = (description == null || description.isBlank())
                ? new Course(name)
                : new Course(name, description);

        courseRepository.persist(course);

        return new CourseResponseDTO(
                course.getId(),
                course.getName(),
                course.getDescription()
        );
    }

    @Transactional
    public List<CourseResponseDTO> getAllCourses() {
        List<Course> courses = courseRepository.listAll();

        if(courses.isEmpty()){
            throw new NoSuchElementException("Nenhum curso encontrado.");
        }

        return courses.stream()
                .map(course -> new CourseResponseDTO(
                        course.getId(),
                        course.getName(),
                        course.getDescription()
                ))
                .toList();
    }

    @Transactional
    public CourseResponseDTO getCourseById(Long id) {
        Course course = courseRepository.findByIdOptional(id).orElseThrow(
                () -> new NoSuchElementException("O curso com a id " + id + " não foi encontrado.")
        );

        return new CourseResponseDTO(
                course.getId(),
                course.getName(),
                course.getDescription()
        );
    }

}
