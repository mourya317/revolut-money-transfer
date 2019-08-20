package revolut.exceptions;

import org.glassfish.jersey.server.ParamException;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.WebApplicationException;
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
public class GenericWebRequestExceptionMapper implements ExceptionMapper<ParamException> {

    class ErrorMessage {
        int status;
        String message;
        String developerMessage;

        public ErrorMessage(int status, String message) {
            this.status = status;
            String[] messages = message.split("\\|");
            this.message = messages[0];
            if(messages.length > 1) {
                this.developerMessage = messages[1];
            }
        }

        public int getStatus() { return this.status; }
        public String getMessage() { return this.message; }
        public String getDeveloperMessage() { return this.developerMessage; }
    }

    public Response toResponse(ParamException ex) {
        Response exResponse = ex.getResponse();
        ErrorMessage errorMessage = new ErrorMessage(exResponse.getStatus(), ex.getMessage());
        return Response.status(exResponse.getStatus()).entity(errorMessage).build();
    }
}
