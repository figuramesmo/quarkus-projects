package ada.caixa.errorhandler;

import io.quarkus.logging.Log;
import io.quarkus.security.AuthenticationFailedException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.LocalDateTime;

@Provider
public class AuthenticationFailedExceptionMapper implements ExceptionMapper<AuthenticationFailedException> {
    @Override
    public Response toResponse(AuthenticationFailedException exception) {
        LocalDateTime currentTime = LocalDateTime.now();
        ErrorResponse errorResponse = new ErrorResponse(
                exception.getMessage(),
                Response.Status.UNAUTHORIZED.getStatusCode(),
                currentTime
        );

        Log.error("Authentication Failed: {}", exception.getMessage(), exception);

        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
