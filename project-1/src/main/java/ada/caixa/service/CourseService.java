package ada.caixa.service;

import ada.caixa.dto.CourseRequestDTO;
import ada.caixa.dto.CourseResponseDTO;
import ada.caixa.entity.Course;
import ada.caixa.repository.CourseRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

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

}
