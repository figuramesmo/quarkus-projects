package ada.caixa.resources;

import ada.caixa.dto.CourseRequestDTO;
import ada.caixa.dto.CourseResponseDTO;
import ada.caixa.service.CourseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/courses")
public class CourseResource {

    private final CourseService courseService;

    public CourseResource(CourseService courseService) {
        this.courseService = courseService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCourse(
            @Valid @NotNull(message = "O Corpo do seu request não pode ser nulo") CourseRequestDTO courseRequestDTO
            ) {
        CourseResponseDTO response = courseService.createCourse(courseRequestDTO);
        return Response
                .status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCourses() {
        return Response.ok(courseService.getAllCourses()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourseById(@PathParam("id") Long id) {
        return Response.ok(courseService.getCourseById(id)).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCourse(
            @PathParam("id") Long id,
            @QueryParam("update_name") boolean name,
            @QueryParam("update_description") boolean description,
            @Valid @NotNull(message = "O corpo do seu request não pode ser nulo") CourseRequestDTO courseRequestDTO
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
