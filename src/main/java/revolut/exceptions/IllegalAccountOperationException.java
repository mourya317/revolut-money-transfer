package revolut.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author Mourya Balla
 * @project banktransfer
 * @CreatedOn 14-08-2019
 */
@Provider
public class IllegalAccountOperationException extends RuntimeException  implements ExceptionMapper<IllegalAccountOperationException> {
    public IllegalAccountOperationException(String message){
        super(message);
    }

    public IllegalAccountOperationException(){
        super();
    }

   /* public IllegalAccountOperationException(String message, Throwable cause) {
        super(message, cause);
    }*/

    /**
     * Map an exception to a {@link Response}. Returning
     * {@code null} results in a {@link Response.Status#NO_CONTENT}
     * response. Throwing a runtime exception results in a
     * {@link Response.Status#INTERNAL_SERVER_ERROR} response.
     *
     * @param exception the exception to map to a response.
     * @return a response mapped from the supplied exception.
     */
    @Override
    public Response toResponse(IllegalAccountOperationException exception) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(exception.getMessage())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
