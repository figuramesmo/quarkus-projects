package ada.caixa.service;

import ada.caixa.dto.CourseRequestDTO;
import ada.caixa.dto.CourseResponseDTO;
import ada.caixa.dto.LessonRequestDTO;
import ada.caixa.dto.LessonResponseDTO;
import ada.caixa.entity.Course;
import ada.caixa.entity.Lesson;
import ada.caixa.repository.CourseRepository;
import ada.caixa.repository.LessonRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.NoSuchElementException;

@ApplicationScoped
public class CourseService {

    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;

    public CourseService(CourseRepository courseRepository, LessonRepository lessonRepository) {
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
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

    @Transactional
    public CourseResponseDTO updateCourse(
            Long id,
            CourseRequestDTO courseRequestDTO,
            boolean name,
            boolean description
    ) {
        Course course = courseRepository.findByIdOptional(id).orElseThrow(
                () -> new NoSuchElementException("O curso com a id " + id + " não foi encontrado.")
        );

        String newName = courseRequestDTO.name();
        String newDescription = courseRequestDTO.description();

        if (name) course.setName(newName);
        if (description) course.setDescription(newDescription);

        courseRepository.persist(course);

        return new CourseResponseDTO(
                course.getId(),
                course.getName(),
                course.getDescription()
        );
    }

    @Transactional
    public void deleteCourse(Long id) {
        Course course = courseRepository.findByIdOptional(id).orElseThrow(
                () -> new NoSuchElementException("O curso com a id " + id + " não foi encontrado.")
        );
        courseRepository.delete(course);
    }

    @Transactional
    public LessonResponseDTO addLessonToCourse(Long courseId, LessonRequestDTO lessonRequestDTO) {
        Course course = courseRepository.findByIdOptional(courseId).orElseThrow(
                () -> new NoSuchElementException("O curso com a id " + courseId + " não foi encontrado.")
        );

        Lesson lesson = new Lesson(lessonRequestDTO.name());

        lessonRepository.persist(lesson);

        course.addLesson(lesson);

        courseRepository.persist(course);

        return new LessonResponseDTO(
                lesson.getId(),
                lesson.getName()
        );
    }
}
