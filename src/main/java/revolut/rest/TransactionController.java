package revolut.rest;

import revolut.dto.AccountRepresentation;
import revolut.dto.TransactionDto;
import revolut.dto.TransactionRepresentation;
import revolut.service.TransactionService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author Mourya Balla
 * @project banktransfer
 * @CreatedOn 19-08-2019
 */
@Path("/transaction")
public class TransactionController {
    private final TransactionService transactionService = TransactionService.getInstance();

    @POST
    @Path("/transact")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response submitMoneyTransfer(TransactionRepresentation transactionRepresentation) {
        TransactionDto txn = transactionService.from(transactionRepresentation);
        List<AccountRepresentation> result = transactionService.transfer(txn);
        return Response.ok().entity(result).cacheControl(CacheControl.valueOf("no-cache")).build();
    }
}
