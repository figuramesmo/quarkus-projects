package ada.caixa.resources;

import ada.caixa.dto.CourseRequestDTO;
import ada.caixa.dto.CourseResponseDTO;
import ada.caixa.service.CourseService;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/courses")
public class CourseResource {

    private final CourseService courseService;

    public CourseResource(CourseService courseService) {
        this.courseService = courseService;
    }

    @POST
    public Response createCourse(
            @Valid CourseRequestDTO courseRequestDTO
            ) {
        CourseResponseDTO response = courseService.createCourse(courseRequestDTO);
        return Response
                .status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

    @GET
    public Response getAllCourses() {
        return Response.ok(courseService.getAllCourses()).build();
    }

    @GET
    @Path("/{id}")
    public Response getCourseById(@PathParam("id") Long id) {
        return Response.ok(courseService.getCourseById(id)).build();
    }
}
