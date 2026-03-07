package ada.caixa.resources;

import ada.caixa.dto.CourseRequestDTO;
import ada.caixa.dto.CourseResponseDTO;
import ada.caixa.service.CourseService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
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

    @PUT
    @Path("/{id}")
    public Response updateCourse(
            @PathParam("id") Long id,
            @QueryParam("update_name") boolean name,
            @QueryParam("update_description") boolean description,
            @Valid CourseRequestDTO courseRequestDTO
    ) {
        CourseResponseDTO response = courseService.updateCourse(id, courseRequestDTO, name, description);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCourse(@PathParam("id") Long id) {
        courseService.deleteCourse(id);
        return Response.noContent().build();
    }
}
