package ada.caixa.errorhandler;

import io.quarkus.logging.Log;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.hibernate.exception.ConstraintViolationException;
import java.time.LocalDateTime;

@Provider
public class HibernateConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        LocalDateTime currentTime = LocalDateTime.now();
        ErrorResponse errorResponse = new ErrorResponse(
                exception.getMessage(),
                Response.Status.CONFLICT.getStatusCode(),
                currentTime
        );

        Log.error("Violação de restrição no banco de dados: {}", exception.getMessage(), exception);

        return Response.status(Response.Status.CONFLICT)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
