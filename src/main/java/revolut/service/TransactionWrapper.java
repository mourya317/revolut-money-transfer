package revolut.service;

import revolut.dto.AccountRepresentation;
import revolut.dto.TransactionDto;

import java.util.List;

/**
 * @author Mourya Balla
 * @project banktransfer
 * @CreatedOn 18-08-2019
 */
public interface TransactionWrapper {
    List<AccountRepresentation> doTransfer(TransactionDto transactionDto);
    //rollbackTransfer(); // We can implement rollback in case of exceptions
}
