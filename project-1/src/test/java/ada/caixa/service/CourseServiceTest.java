package ada.caixa.service;

import ada.caixa.dto.CourseRequestDTO;
import ada.caixa.dto.CourseResponseDTO;
import ada.caixa.entity.Course;
import ada.caixa.repository.CourseRepository;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class CourseServiceTest {

    @Inject
    CourseService courseService;

    @Inject
    CourseRepository courseRepository;

    // De acordo com https://pt.quarkus.io/guides/getting-started-testing
    // A anotação @TestTransaction é usada para garantir que cada teste seja
    // executado dentro de uma transação e rolledback.
    // Portanto não é necessário a limpeza dos dados manualmente antes de cada
    // teste, pois a transação será revertida automaticamente.

    // Para aprofundar depois:
    // https://www.baeldung.com/java-quarkus-testing
    // Testes com mock e com uma DB exclusiva pros testes

    // Pra aprofundar mais:
    // https://stackoverflow.com/questions/61510701/what-is-the-best-approach-to-perform-endpoints-unit-tests-in-quarkus
    // resposta com muitas referências


//    @BeforeEach
//    @Transactional
//    void setUp() {
//        courseRepository.deleteAll();
//    }

    @Test
    @TestTransaction
    void testCreateCourseWithDescription() {
        CourseRequestDTO request = new CourseRequestDTO("Java Basico", "Introdução ao Java");
        CourseResponseDTO response = courseService.createCourse(request);

        assertNotNull(response);
        assertNotNull(response.id());
        assertEquals("Java Basico", response.name());
        assertEquals("Introdução ao Java", response.description());
    }

    @Test
    @TestTransaction
    void testCreateCourseEmptyDescription() {
        CourseRequestDTO request = new CourseRequestDTO("Java Basico", "");
        CourseResponseDTO response = courseService.createCourse(request);

        assertNotNull(response);
        assertNotNull(response.id());
        assertEquals("Java Basico", response.name());
        assertNull(response.description());
    }

    @Test
    @TestTransaction
    void testCreateCourseNullDescription() {
        CourseRequestDTO request = new CourseRequestDTO("Java Basico", null);
        CourseResponseDTO response = courseService.createCourse(request);

        assertNotNull(response);
        assertNotNull(response.id());
        assertEquals("Java Basico", response.name());
        assertNull(response.description());
    }

    @Test
    @TestTransaction
    void testListAllCoursesNotNull(){
        assertTrue(courseRepository.listAll().isEmpty());

        Course course = new Course("Java Basico", "Curso introdutorio");
        courseRepository.persist(course);

        Course anotherCourse = new Course("Java Avançado", "Curso difícil");
        courseRepository.persist(anotherCourse);

        assertTrue(courseRepository.listAll().size() > 1);

        List<CourseResponseDTO> listCourses = courseService.getAllCourses();

        assertNotNull(listCourses);
        assertEquals(2, listCourses.size());
    }

    @Test
    @TestTransaction
    void testGetAllCoursesEmptyList() {
        assertTrue(courseRepository.listAll().isEmpty());

        Exception exception = assertThrows(
                NoSuchElementException.class,
                () -> courseService.getAllCourses()
        );

        assertEquals("Nenhum curso encontrado.", exception.getMessage());
    }

    @Test
    @TestTransaction
    void testGetCourseById() {
        assertTrue(courseRepository.listAll().isEmpty());
        Course course = new Course("Java Basico", "Curso introdutorio");
        courseRepository.persist(course);

        CourseResponseDTO response = courseService.getCourseById(course.getId());

        assertNotNull(response);
        assertEquals(course.getId(), response.id());
        assertEquals(course.getName(), response.name());
        assertEquals(course.getDescription(), response.description());
    }


    @Test
    @TestTransaction
    void testGetCourseByIdEmpty() {
        assertTrue(courseRepository.listAll().isEmpty());

        Exception exception = assertThrows(
                NoSuchElementException.class,
                () -> courseService.getCourseById(666L)
        );

        assertEquals("O curso com a id 666 não foi encontrado.", exception.getMessage());
    }

    @ParameterizedTest
    @TestTransaction
    @CsvSource({
            "true,true,Novo Nome,Nova Descrição",
            "true,false,Novo Nome,Intro",
            "false,true,Java,Nova Descrição",
            "false,false,Java,Intro"
    })
    void updateCourse(boolean updateName, boolean updateDescription,
                      String expectedName, String expectedDescription) {

        Course course = new Course();
        course.setName("Java");
        course.setDescription("Intro");
        courseRepository.persist(course);

        CourseRequestDTO dto = new CourseRequestDTO("Novo Nome", "Nova Descrição");

        CourseResponseDTO res = courseService.updateCourse(
                course.getId(),
                dto,
                updateName,
                updateDescription
        );

        assertEquals(expectedName, res.name());
        assertEquals(expectedDescription, res.description());
    }

    @Test
    void courseNotFound() {

        CourseRequestDTO dto = new CourseRequestDTO("Novo Nome", "Nova Descrição");

        NoSuchElementException ex = assertThrows(
                NoSuchElementException.class,
                () -> courseService.updateCourse(999L, dto, true, true)
        );

        assertEquals("O curso com a id 999 não foi encontrado.", ex.getMessage());
    }

    @Test
    @TestTransaction
    void deleteCourseSuccess() {
        assertTrue(courseRepository.listAll().isEmpty());

        Course course = new Course();
        course.setName("Java");
        course.setDescription("Intro");
        courseRepository.persist(course);

        assertEquals(1, courseRepository.listAll().size());

        Long id = course.getId();

        courseService.deleteCourse(id);

        assertTrue(courseRepository.listAll().isEmpty());
    }

    @Test
    void deleteCourseNotFound() {

        NoSuchElementException ex = assertThrows(
                NoSuchElementException.class,
                () -> courseService.deleteCourse(999L)
        );

        assertEquals(
                "O curso com a id 999 não foi encontrado.",
                ex.getMessage()
        );
    }
}