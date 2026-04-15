package ada.caixa.resources;

import ada.caixa.dto.*;
import ada.caixa.service.CourseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

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
        URI location = URI.create("/courses/" + response.id());
        return Response
                .created(location)
                .status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

    // Método sem paginação
    // O testes do professor desse endpoint só funcionam com esse método
    /*
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCourses() {
        return Response.ok(courseService.getAllCourses()).build();
    }
    */

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCourses(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("5") int size
    ) {
        PaginatedResponseDTO<CourseResponseDTO> cursos = courseService.getAllCourses(page, size);
        return Response.ok(cursos).type(MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourseById(@PathParam("id") Long id) {
        return Response.ok(courseService.getCourseById(id)).build();
    }

    // O seguinte método usa query params para indicar quais campos devem
    // ser atualizados. Diverge um pouco da implementação do professor
    // porque minha entidade "Curso" tem mais campos do que os mínimos solicitados
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

    @POST
    @Path("/{id}/lessons")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addLessonToCourse(
            @PathParam("id") Long courseId,
            @Valid @NotNull(message = "O corpo do seu request não pode ser nulo") LessonRequestDTO lessonRequestDTO
    ) {
        LessonResponseDTO response = courseService.addLessonToCourse(courseId, lessonRequestDTO);
        return Response
                .status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

    @GET
    @Path("/{id}/lessons")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLessonsByCourseId(@PathParam("id") Long courseId){
        return Response.ok(courseService.getLessonsByCourseId(courseId)).build();
    }
}
