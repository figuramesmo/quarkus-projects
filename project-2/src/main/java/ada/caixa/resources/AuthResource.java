package ada.caixa.resources;

import ada.caixa.dto.JWTokenResponseDTO;
import ada.caixa.dto.UserSignInRequestDTO;
import ada.caixa.security.JWTGenerator;
import ada.caixa.service.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
public class AuthResource {

    private final JWTGenerator jwtGenerator;
    private final AuthService authService;

    public AuthResource(JWTGenerator jwtGenerator, AuthService authService) {
        this.jwtGenerator = jwtGenerator;
        this.authService = authService;
    }

    /*
    @POST
    @Path("/token")
    public Response generateToken() {
        JWTokenResponseDTO token = jwtGenerator.generateToken(3600L);
        return Response.ok(token).build();
    }
    */


    @POST
    @Path("/token")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signIn(
            @Valid
            @NotNull(message = "O Corpo do seu request não pode ser nulo")
            UserSignInRequestDTO userSignInRequestDTO
    ) {
        JWTokenResponseDTO token = authService.signIn(userSignInRequestDTO);
        return Response.ok(token).build();
    }
}
