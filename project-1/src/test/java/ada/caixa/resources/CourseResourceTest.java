package ada.caixa.resources;

import ada.caixa.dto.CourseRequestDTO;
import ada.caixa.dto.CourseResponseDTO;
import ada.caixa.dto.PaginatedResponseDTO;
import ada.caixa.service.CourseService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@QuarkusTest
class CourseResourceTest {

    @InjectMock
    CourseService courseService;

    @Test
    void createCourse() {

        CourseRequestDTO request = new CourseRequestDTO("Java", "Intro");
        CourseResponseDTO response = new CourseResponseDTO(1L, "Java", "Intro");

        when(courseService.createCourse(request)).thenReturn(response);

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .when()
                .post("/courses")
                .then()
                .statusCode(201)
                .body("id", is(1))
                .body("name", is("Java"))
                .body("description", is("Intro"));
    }

    @Test
    void getAllCourses() {

        List<CourseResponseDTO> courses = List.of(
                new CourseResponseDTO(1L, "Java", "Intro")
        );

        PaginatedResponseDTO<CourseResponseDTO> paginatedCourses = new PaginatedResponseDTO<>(
                0,
                5,
                1,
                1,
                courses
        );

        when(courseService.getAllCourses(anyInt(), anyInt())).thenReturn(paginatedCourses);

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .when()
                .get("/courses")
                .then()
                .statusCode(200)
                .body("page", is(0));
    }

    @Test
    void getCourseById() {

        CourseResponseDTO response = new CourseResponseDTO(1L, "Java", "Intro");

        when(courseService.getCourseById(1L)).thenReturn(response);

        given()
                .when()
                .get("/courses/1")
                .then()
                .statusCode(200)
                .body("id", is(1))
                .body("name", is("Java"));
    }

    @Test
    void updateCourse() {

        CourseRequestDTO request = new CourseRequestDTO("Java Avançado", "Novo");

        CourseResponseDTO response =
                new CourseResponseDTO(1L, "Java Avançado", "Novo");

        when(courseService.updateCourse(1L, request, true, true))
                .thenReturn(response);

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("update_name", true)
                .queryParam("update_description", true)
                .body(request)
                .when()
                .put("/courses/1")
                .then()
                .statusCode(200)
                .body("name", is("Java Avançado"));
    }

    @Test
    void deleteCourse() {

        given()
                .when()
                .delete("/courses/1")
                .then()
                .statusCode(204);
    }

}