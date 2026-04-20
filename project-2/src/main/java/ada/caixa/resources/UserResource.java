package ada.caixa.resources;

import ada.caixa.dto.UserRequestDTO;
import ada.caixa.dto.UserResponseDTO;
import ada.caixa.dto.UserSignInRequestDTO;
import ada.caixa.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(
            @Valid
            @NotNull(message = "O Corpo do seu request não pode ser nulo")
            UserRequestDTO userRequestDTO
    ) {
        UserResponseDTO response = userService.createUser(userRequestDTO);
        return Response
                .status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

    @GET
    @Path("/me")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "USER"})
    public Response getMyCredentials(){
        UserResponseDTO response = userService.getUserCredentialsAuthenticated();
        return Response.ok(response).build();
    }
}
