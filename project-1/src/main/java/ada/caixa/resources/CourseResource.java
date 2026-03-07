package ada.caixa.resources;

import ada.caixa.dto.CourseDTO;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/courses")
public class CourseResource {

    @POST
    public Response createCourse(
            @Valid CourseDTO courseDTO
            ) {
        // Lógica para criar um curso
        return Response.status(Response.Status.CREATED).build();
    }
}
