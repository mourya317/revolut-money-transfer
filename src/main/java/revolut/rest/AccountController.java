package revolut.rest;

import revolut.model.Account;
import revolut.repository.BankRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;

/**
 * @author Mourya Balla
 * @project banktransfer
 * @CreatedOn 19-08-2019
 */
@Path("/accounts")
public class AccountController {

    private final BankRepository repository = BankRepository.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllAccounts() {
        return Response.ok(Collections.unmodifiableCollection(repository.getAllAccounts())).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAccountById(@PathParam("id") String id) {
        Account account = repository.get(id);
        if (account == null)
            return Response.noContent().build();

        return Response.ok(account).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewAccount(Account account) {
        repository.addAccount(account);
        return Response.ok(account).build();
    }

}
