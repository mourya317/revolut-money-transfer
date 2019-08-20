package revolut.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * @author Mourya Balla
 * @project banktransfer
 * @CreatedOn 14-08-2019
 */
@Path("/health")
public class TestResource {

    @GET
    @Path("/status")
    public Response healthCheck(){
        return Response
                .ok("Healthy.")
                .build();
    }

}
